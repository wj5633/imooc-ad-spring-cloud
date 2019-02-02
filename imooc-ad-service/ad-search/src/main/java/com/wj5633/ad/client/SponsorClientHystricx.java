package com.wj5633.ad.client;

import com.wj5633.ad.client.vo.AdPlan;
import com.wj5633.ad.client.vo.AdPlanGetRequest;
import com.wj5633.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/1 22:41
 * @description
 */

@Component
public class SponsorClientHystricx implements SponsorClient {

    @Override
    public CommonResponse<List<AdPlan>> listAdPlansByRebbon(AdPlanGetRequest request) {
        return CommonResponse.ofError("eureka-client-ad-sponsor error");
    }
}
