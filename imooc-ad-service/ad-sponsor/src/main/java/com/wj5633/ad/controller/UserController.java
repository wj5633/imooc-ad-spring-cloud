package com.wj5633.ad.controller;

import com.alibaba.fastjson.JSON;
import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.service.IUserService;
import com.wj5633.ad.vo.AdUserRequest;
import com.wj5633.ad.vo.AdUserResponse;
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
 * @create 2019/1/30 23:25
 * @description
 */
@Slf4j
@RestController
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public AdUserResponse createUser(@RequestBody AdUserRequest request) throws AdException {
        log.info("ad-sponsor: createUser -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
