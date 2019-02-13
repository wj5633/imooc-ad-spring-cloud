package com.wj5633.ad.service.impl;

import com.wj5633.ad.Application;
import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.service.IAdPlanService;
import com.wj5633.ad.vo.AdPlanGetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-13 下午3:50
 * @description
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdPlanServiceImplTest {

    @Autowired
    private IAdPlanService planService;

    @Test
    public void createAdPlan() throws AdException {
    }

    @Test
    public void getAdPlanByIds() throws AdException {
        System.out.println(
                planService.getAdPlanByIds(
                        new AdPlanGetRequest(15L, Collections.singletonList(10L))
                )
        );
    }

    @Test
    public void updateAdPlan() throws AdException {
    }

    @Test
    public void deleteAdPlan() throws AdException {
    }
}