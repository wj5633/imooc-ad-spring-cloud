package com.wj5633.ad.service.impl;

import com.wj5633.ad.constant.CommonStatus;
import com.wj5633.ad.constant.ErrorConstant;
import com.wj5633.ad.dao.AdPlanRepository;
import com.wj5633.ad.dao.AdUserRepository;
import com.wj5633.ad.entity.AdPlan;
import com.wj5633.ad.entity.AdUser;
import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.service.IAdPlanService;
import com.wj5633.ad.utils.CommonUtils;
import com.wj5633.ad.vo.AdPlanGetRequest;
import com.wj5633.ad.vo.AdPlanRequest;
import com.wj5633.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 1:37
 * @description
 */

@Slf4j
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private AdUserRepository userRepository;
    private AdPlanRepository planRepository;


    @Autowired
    public AdPlanServiceImpl(AdUserRepository userRepository, AdPlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(ErrorConstant.REQUEST_PARAM_ERROR);
        }
        Optional<AdUser> adUser = userRepository.findById(request.getUserId());
        if (!adUser.isPresent()) {
            throw new AdException(ErrorConstant.CAN_NOT_FIND_RECORD_ERROR);
        }

        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldPlan != null) {
            throw new AdException(ErrorConstant.PLANNAMR_EXIST_ERROR);
        }
        AdPlan newPlan = planRepository.save(new AdPlan(
                request.getUserId(), request.getPlanName(),
                CommonUtils.parseStringToDate(request.getStartDate()),
                CommonUtils.parseStringToDate(request.getEndDate())
        ));
        return new AdPlanResponse(newPlan.getId(), newPlan.getUserId(), newPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(ErrorConstant.REQUEST_PARAM_ERROR);
        }
        return planRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(ErrorConstant.REQUEST_PARAM_ERROR);
        }
        AdPlan adPlan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (adPlan == null) {
            throw new AdException(ErrorConstant.CAN_NOT_FIND_RECORD_ERROR);
        }
        if (request.getPlanName() != null) {
            adPlan.setPlanName(request.getPlanName());
        }
        if (request.getStartDate() != null) {
            adPlan.setStartDate(CommonUtils.parseStringToDate(request.getStartDate()));
        }
        if (request.getEndDate() != null) {
            adPlan.setEndDate(CommonUtils.parseStringToDate(request.getEndDate()));
        }
        adPlan.setUpdateTime(new Date());
        adPlan = planRepository.save(adPlan);
        return new AdPlanResponse(adPlan.getId(), adPlan.getUserId(), adPlan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws Exception {
        if (!request.deleteValidate()) {
            throw new AdException(ErrorConstant.REQUEST_PARAM_ERROR);
        }

        AdPlan adPlan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (adPlan == null) {
            throw new AdException(ErrorConstant.CAN_NOT_FIND_RECORD_ERROR);
        }

        adPlan.setPlanStatus(CommonStatus.INVALID.getStatus());
        adPlan.setUpdateTime(new Date());
        planRepository.save(adPlan);
    }
}
