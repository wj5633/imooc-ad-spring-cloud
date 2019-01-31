package com.wj5633.ad.service;

import com.wj5633.ad.entity.AdPlan;
import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.vo.AdPlanGetRequest;
import com.wj5633.ad.vo.AdPlanRequest;
import com.wj5633.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 1:37
 * @description
 */
public interface IAdPlanService {

    /**
     * 创建推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 获取推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    /**
     * 更新推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划
     *
     * @param request
     * @throws Exception
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
