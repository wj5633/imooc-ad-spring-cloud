package com.wj5633.ad.search.impl;

import com.wj5633.ad.index.DataTable;
import com.wj5633.ad.index.adunit.AdUnitIndex;
import com.wj5633.ad.search.ISearch;
import com.wj5633.ad.search.vo.SearchRequest;
import com.wj5633.ad.search.vo.SearchResponse;
import com.wj5633.ad.search.vo.feature.DistrictFeature;
import com.wj5633.ad.search.vo.feature.FeatureRelation;
import com.wj5633.ad.search.vo.feature.ItFeature;
import com.wj5633.ad.search.vo.feature.KeywordFeature;
import com.wj5633.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午6:17
 * @description
 */

@Slf4j
@Component
public class SearchImpl implements ISearch {

    @Override
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
        }

        return null;
    }
}
