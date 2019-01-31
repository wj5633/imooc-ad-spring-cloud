package com.wj5633.ad.service;

import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.vo.CreativeRequest;
import com.wj5633.ad.vo.CreativeResponse;
import com.wj5633.ad.vo.CreativeUnitRequest;
import com.wj5633.ad.vo.CreativeUnitResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 23:10
 * @description
 */

public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request) throws AdException;

}
