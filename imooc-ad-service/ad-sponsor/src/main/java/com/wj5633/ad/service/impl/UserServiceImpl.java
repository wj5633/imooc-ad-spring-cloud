package com.wj5633.ad.service.impl;

import com.wj5633.ad.constant.ErrorConstant;
import com.wj5633.ad.dao.AdUserRepository;
import com.wj5633.ad.entity.AdUser;
import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.service.IUserService;
import com.wj5633.ad.utils.CommonUtils;
import com.wj5633.ad.vo.AdUserRequest;
import com.wj5633.ad.vo.AdUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 1:26
 * @description
 */

@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AdUserResponse createUser(AdUserRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(ErrorConstant.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(ErrorConstant.USERNAME_EXIST_ERROR);
        }

        AdUser newUser = userRepository.save(new AdUser(
                request.getUsername(), CommonUtils.md5(request.getUsername())
        ));

        return new AdUserResponse(newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(), newUser.getUpdateTime());
    }
}
