package com.wj5633.ad.search.vo;

import com.wj5633.ad.search.vo.feature.DistrictFeature;
import com.wj5633.ad.search.vo.feature.FeatureRelation;
import com.wj5633.ad.search.vo.feature.ItFeature;
import com.wj5633.ad.search.vo.feature.KeywordFeature;
import com.wj5633.ad.search.vo.media.AdSlot;
import com.wj5633.ad.search.vo.media.App;
import com.wj5633.ad.search.vo.media.Device;
import com.wj5633.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午5:35
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    // 媒体方的请求标识
    private String mediaId;

    // 请求基本信息
    private RequestInfo requestInfo;

    // 请求匹配信息
    private FeatureInfo featureInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo {

        private String requestId;

        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo {

        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;

        private FeatureRelation relation = FeatureRelation.AND;
    }

}
