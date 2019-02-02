package com.wj5633.ad.controller;

import com.alibaba.fastjson.JSON;
import com.wj5633.ad.annotation.IgnoreResponseAdvice;
import com.wj5633.ad.client.SponsorClient;
import com.wj5633.ad.client.vo.AdPlan;
import com.wj5633.ad.client.vo.AdPlanGetRequest;
import com.wj5633.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/1 22:27
 * @description
 */

@Slf4j
@RestController
public class SearchController {

    private final RestTemplate restTemplate;
    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @IgnoreResponseAdvice
    @PostMapping("/listAdPlans")
    public CommonResponse<List<AdPlan>> listAdPlans(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: listAdPlans -> {}", JSON.toJSONString(request));
        return sponsorClient.listAdPlansByRebbon(request);
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/listAdPlansByRebbon")
    public CommonResponse<List<AdPlan>> listAdPlansByRebbon(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: listAdPlansByRebbon -> {}", JSON.toJSONString(request));

        return restTemplate.postForEntity(
                "http://eureka-client-ad-sponsor/ad-sponsor/plan/list",
                request,
                CommonResponse.class
        ).getBody();
    }
}
