package com.wj5633.ad.service.impl;

import com.wj5633.ad.dao.CreativeRepository;
import com.wj5633.ad.entity.Creative;
import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.service.ICreativeService;
import com.wj5633.ad.vo.CreativeRequest;
import com.wj5633.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 23:11
 * @description
 */

@Slf4j
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    @Transactional
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {
        Creative creative = creativeRepository.save(request.convetToEntity());
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
