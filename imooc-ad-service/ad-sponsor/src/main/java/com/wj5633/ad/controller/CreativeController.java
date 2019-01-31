package com.wj5633.ad.controller;

import com.alibaba.fastjson.JSON;
import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.service.ICreativeService;
import com.wj5633.ad.vo.CreativeRequest;
import com.wj5633.ad.vo.CreativeResponse;
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
 * @create 2019/1/30 23:48
 * @description
 */

@Slf4j
@RestController
public class CreativeController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/creative/create")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdException {
        log.info("ad-sponsor: creativeService -> {}", JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }

}
