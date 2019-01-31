package com.wj5633.ad.controller;

import com.alibaba.fastjson.JSON;
import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.service.IAdUnitService;
import com.wj5633.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 23:39
 * @description
 */
@Slf4j
@RestController
public class AdUnitController {
    private final IAdUnitService adUnitService;

    @Autowired
    public AdUnitController(IAdUnitService adUnitService) {
        this.adUnitService = adUnitService;
    }

    @PostMapping("/unit/create")
    public AdUnitResponse createUnit(@RequestBody AdUnitRequest request) throws AdException {
        log.info("ad-sponsor: createUnit -> {}", JSON.toJSONString(request));
        return adUnitService.createUnit(request);
    }

    @PostMapping("/unit/keyword/create")
    public AdUnitKeywordResponse createUnitKeyword(@RequestBody AdUnitKeywordRequest request) throws AdException {
        log.info("ad-sponsor: createUnitKeyword -> {}", JSON.toJSONString(request));
        return adUnitService.createUnitKeyword(request);
    }

    @PostMapping("/unit/it/create")
    public AdUnitItResponse createUnitIt(@RequestBody AdUnitItRequest request) throws AdException {
        log.info("ad-sponsor: createUnitIt -> {}", JSON.toJSONString(request));
        return adUnitService.createUnitIt(request);
    }

    @PostMapping("/unit/district/create")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictRequest request) throws AdException {
        log.info("ad-sponsor: createUnitDistrict -> {}", JSON.toJSONString(request));
        return adUnitService.createUnitDistrict(request);
    }

    @PostMapping("/unit/creative/create")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException {
        log.info("ad-sponsor: createCreativeUnit -> {}", JSON.toJSONString(request));
        return adUnitService.createCreativeUnit(request);
    }
}
