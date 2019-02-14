package com.wj5633.ad.service;

import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.vo.AdUserRequest;
import com.wj5633.ad.vo.AdUserResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 1:22
 * @description
 */
public interface IUserService {

    /**
     * 创建用户
     * @param request
     * @return
     * @throws AdException
     */
    AdUserResponse createUser(AdUserRequest request) throws AdException;
}