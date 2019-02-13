package com.wj5633.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wj5633.ad.index.CommonStatus;
import com.wj5633.ad.index.DataTable;
import com.wj5633.ad.index.adunit.AdUnitIndex;
import com.wj5633.ad.index.adunit.AdUnitObject;
import com.wj5633.ad.index.creative.CreativeIndex;
import com.wj5633.ad.index.creative.CreativeObject;
import com.wj5633.ad.index.creativeunit.CreativeUnitIndex;
import com.wj5633.ad.index.district.UnitDistrictIndex;
import com.wj5633.ad.index.interest.UnitItIndex;
import com.wj5633.ad.index.keyword.UnitKeywordIndex;
import com.wj5633.ad.search.ISearch;
import com.wj5633.ad.search.vo.SearchRequest;
import com.wj5633.ad.search.vo.SearchResponse;
import com.wj5633.ad.search.vo.feature.DistrictFeature;
import com.wj5633.ad.search.vo.feature.FeatureRelation;
import com.wj5633.ad.search.vo.feature.ItFeature;
import com.wj5633.ad.search.vo.feature.KeywordFeature;
import com.wj5633.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午6:17
 * @description
 */

@Slf4j
@Service
public class SearchImpl implements ISearch {

    public SearchResponse fetchAdsFallback(SearchRequest request, Throwable t) {
        return null;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fetchAdsFallback")
    public SearchResponse fetchAds(SearchRequest request) {
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();

        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();

        FeatureRelation relation = request.getFeatureInfo().getRelation();

        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();

        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;
            // 根据流量类型获取初始 AdUnit
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());

            if (relation == FeatureRelation.AND) {
                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItFeature(adUnitIdSet, itFeature);

                targetUnitIdSet = adUnitIdSet;
            } else {
                targetUnitIdSet = getOrRelationUnitIds(
                        adUnitIdSet,
                        keywordFeature,
                        districtFeature,
                        itFeature);
            }
            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);

            filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);

            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            List<CreativeObject> creativeObjects = DataTable.of(CreativeIndex.class).fetch(adIds);

            // 通过 AdSLot 实现对 creativeObjects 过滤
            filterCreativeByAdSlot(
                    creativeObjects,
                    adSlot.getWidth(),
                    adSlot.getHeight(),
                    adSlot.getTypes()
            );

            adSlot2Ads.put(
                    adSlot.getAdSlotCode(), buildCreativeResponse(creativeObjects)
            );
        }
        log.info("fetchAds: {}-{}", JSON.toJSONString(request), JSON.toJSONString(response));

        return response;
    }

    private Set<Long> getOrRelationUnitIds(Set<Long> adUnitIds,
                                           KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature,
                                           ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return Collections.emptySet();
        }
        Set<Long> keywordUnitIds = new HashSet<>(adUnitIds);
        Set<Long> districtUnitIds = new HashSet<>(adUnitIds);
        Set<Long> itUnitIds = new HashSet<>(adUnitIds);
        filterKeywordFeature(keywordUnitIds, keywordFeature);
        filterDistrictFeature(districtUnitIds, districtFeature);
        filterItFeature(itUnitIds, itFeature);

        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIds, districtUnitIds),
                        itUnitIds
                ));
    }

    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(keywordFeature.getKetwords())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitKeywordIndex.class).match(adUnitId, keywordFeature.getKetwords())
            );
        }
    }

    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitDistrictIndex.class).match(adUnitId, districtFeature.getDistricts())
            );
        }
    }

    private void filterItFeature(Collection<Long> adUnitIds, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitItIndex.class).match(adUnitId, itFeature.getIts())
            );
        }
    }

    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects, CommonStatus status) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }
        CollectionUtils.filter(
                unitObjects,
                object ->
                        object.getUnitStatus().equals(status.getStatus())
                                && object.getAdPlanObject().getPlanStatus().equals(status.getStatus())
        );
    }

    private void filterCreativeByAdSlot(List<CreativeObject> creativeObjects,
                                        Integer width,
                                        Integer height,
                                        List<Integer> types) {
        if (CollectionUtils.isEmpty(creativeObjects)) {
            return;
        }

        CollectionUtils.filter(
                creativeObjects,
                object ->
                        object.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                                && object.getWidth().equals(width)
                                && object.getHeight().equals(height)
                                && types.contains(object.getType())
        );
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creativeObjects) {
        if (CollectionUtils.isEmpty(creativeObjects)) {
            return Collections.emptyList();
        }

        CreativeObject randomObject = creativeObjects.get(
                Math.abs(new Random().nextInt()) % creativeObjects.size()
        );

        return Collections.singletonList(
                SearchResponse.convert(randomObject)
        );
    }
}
