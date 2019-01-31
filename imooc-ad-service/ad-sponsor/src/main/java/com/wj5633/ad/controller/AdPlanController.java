package com.wj5633.ad.controller;

import com.alibaba.fastjson.JSON;
import com.wj5633.ad.entity.AdPlan;
import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.service.IAdPlanService;
import com.wj5633.ad.vo.AdPlanGetRequest;
import com.wj5633.ad.vo.AdPlanRequest;
import com.wj5633.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 23:29
 * @description
 */

@Slf4j
@RestController
public class AdPlanController {
    private final IAdPlanService adPlanService;

    @Autowired
    public AdPlanController(IAdPlanService adPlanService) {
        this.adPlanService = adPlanService;
    }

    @PostMapping("/plan/create")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: createAdPlan -> {}", JSON.toJSONString(request));
        return adPlanService.createAdPlan(request);
    }

    @PostMapping("/plan/list")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest request) throws AdException {
        log.info("ad-sponsor: getAdPlanByIds -> {}", JSON.toJSONString(request));
        return adPlanService.getAdPlanByIds(request);
    }

    @PostMapping("/plan/update")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: updateAdPlan -> {}", JSON.toJSONString(request));
        return adPlanService.updateAdPlan(request);
    }

    @PostMapping("/plan/delete")
    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: deleteAdPlan -> {}", JSON.toJSONString(request));
        adPlanService.deleteAdPlan(request);
    }
}
