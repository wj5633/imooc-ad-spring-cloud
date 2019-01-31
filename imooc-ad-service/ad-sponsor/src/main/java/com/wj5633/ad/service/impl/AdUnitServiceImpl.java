package com.wj5633.ad.service.impl;

import com.wj5633.ad.constant.ErrorConstant;
import com.wj5633.ad.dao.AdPlanRepository;
import com.wj5633.ad.dao.AdUnitRepository;
import com.wj5633.ad.dao.CreativeRepository;
import com.wj5633.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.wj5633.ad.dao.unit_condition.AdUnitItRepository;
import com.wj5633.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.wj5633.ad.dao.unit_condition.CreativeUnitRepository;
import com.wj5633.ad.entity.AdPlan;
import com.wj5633.ad.entity.AdUnit;
import com.wj5633.ad.entity.unit_condition.AdUnitDistrict;
import com.wj5633.ad.entity.unit_condition.AdUnitIt;
import com.wj5633.ad.entity.unit_condition.AdUnitKeyword;
import com.wj5633.ad.entity.unit_condition.CreativeUnit;
import com.wj5633.ad.exception.AdException;
import com.wj5633.ad.service.IAdUnitService;
import com.wj5633.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/30 22:27
 * @description
 */

@Slf4j
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdPlanRepository planRepository;
    private final AdUnitRepository unitRepository;
    private final AdUnitKeywordRepository unitKeywordRepository;
    private final AdUnitItRepository unitItRepository;
    private final AdUnitDistrictRepository unitDistrictRepository;
    private final CreativeRepository creativeRepository;
    private final CreativeUnitRepository creativeUnitRepository;

    @Autowired
    public AdUnitServiceImpl(AdPlanRepository planRepository,
                             AdUnitRepository unitRepository,
                             AdUnitKeywordRepository unitKeywordRepository,
                             AdUnitItRepository unitItRepository,
                             AdUnitDistrictRepository unitDistrictRepository,
                             CreativeUnitRepository creativeUnitRepository,
                             CreativeRepository creativeRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitItRepository = unitItRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.creativeUnitRepository = creativeUnitRepository;
        this.creativeRepository = creativeRepository;
    }

    @Override
    @Transactional
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(ErrorConstant.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> adPlan = planRepository.findById(request.getPlanId());
        if (!adPlan.isPresent()) {
            throw new AdException(ErrorConstant.CAN_NOT_FIND_RECORD_ERROR);
        }

        AdUnit oldUnit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (oldUnit != null) {
            throw new AdException(ErrorConstant.UNIT_EXIST_ERROR);
        }

        AdUnit newUnit = unitRepository.save(new AdUnit(
                request.getPlanId(), request.getUnitName(), request.getPositionType(), request.getBudget()
        ));

        return new AdUnitResponse(newUnit.getPlanId(), newUnit.getUnitName());
    }

    @Override
    @Transactional
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {

        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(AdUnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(ErrorConstant.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();
        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            request.getUnitKeywords().forEach(i -> unitKeywords.add(
                    new AdUnitKeyword(i.getUnitId(), i.getKeyword())
            ));
            ids = unitKeywordRepository.saveAll(unitKeywords).stream()
                    .map(AdUnitKeyword::getId)
                    .collect(Collectors.toList());
        }
        return new AdUnitKeywordResponse(ids);
    }

    @Override
    @Transactional
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream()
                .map(AdUnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(ErrorConstant.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();
        List<AdUnitIt> unitIts = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitIts())) {
            request.getUnitIts().forEach(i -> unitIts.add(
                    new AdUnitIt(i.getUnitId(), i.getItTag())
            ));
            ids = unitItRepository.saveAll(unitIts).stream()
                    .map(AdUnitIt::getId)
                    .collect(Collectors.toList());
        }
        return new AdUnitItResponse(ids);
    }

    @Override
    @Transactional
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getUnitDistricts().stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(ErrorConstant.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();
        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitDistricts())) {
            request.getUnitDistricts().forEach(i -> unitDistricts.add(
                    new AdUnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())
            ));
            ids = unitDistrictRepository.saveAll(unitDistricts).stream()
                    .map(AdUnitDistrict::getId)
                    .collect(Collectors.toList());
        }
        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {

        List<Long> unitIds = request.getCreativeUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getUnitId)
                .collect(Collectors.toList());
        List<Long> creativeIds = request.getCreativeUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());

        if (!isRelatedUnitExist(unitIds) || !isRelatedCreativeExist(creativeIds)) {
            throw new AdException(ErrorConstant.REQUEST_PARAM_ERROR);
        }
        List<CreativeUnit> creativeUnits = new ArrayList<>();
        request.getCreativeUnitItems().forEach(i -> creativeUnits.add(
                new CreativeUnit(i.getCreativeId(), i.getUnitId())
        ));
        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits).stream()
                .map(CreativeUnit::getId)
                .collect(Collectors.toList());

        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }

        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }
        return creativeRepository.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
    }
}
