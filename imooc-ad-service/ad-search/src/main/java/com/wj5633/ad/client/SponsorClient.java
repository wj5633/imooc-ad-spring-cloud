package com.wj5633.ad.client;

import com.wj5633.ad.client.vo.AdPlan;
import com.wj5633.ad.client.vo.AdPlanGetRequest;
import com.wj5633.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/1 22:36
 * @description
 */

@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystricx.class)
public interface SponsorClient {

    @RequestMapping(value = "/ad-sponsor/plan/list", method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> listAdPlansByRebbon(@RequestBody AdPlanGetRequest request);
}
