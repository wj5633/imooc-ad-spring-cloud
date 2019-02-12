package com.wj5633.ad.service;

import com.alibaba.fastjson.JSON;
import com.wj5633.ad.DumpApplication;
import com.wj5633.ad.constant.CommonStatus;
import com.wj5633.ad.dao.AdPlanRepository;
import com.wj5633.ad.dao.AdUnitRepository;
import com.wj5633.ad.dao.CreativeRepository;
import com.wj5633.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.wj5633.ad.dao.unit_condition.AdUnitItRepository;
import com.wj5633.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.wj5633.ad.dao.unit_condition.CreativeUnitRepository;
import com.wj5633.ad.dump.DConstant;
import com.wj5633.ad.dump.table.*;
import com.wj5633.ad.entity.AdPlan;
import com.wj5633.ad.entity.AdUnit;
import com.wj5633.ad.entity.Creative;
import com.wj5633.ad.entity.unit_condition.AdUnitDistrict;
import com.wj5633.ad.entity.unit_condition.AdUnitIt;
import com.wj5633.ad.entity.unit_condition.AdUnitKeyword;
import com.wj5633.ad.entity.unit_condition.CreativeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-2 下午2:26
 * @description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DumpApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {
    @Autowired
    private AdPlanRepository planRepository;
    @Autowired
    private AdUnitRepository unitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private AdUnitKeywordRepository unitKeywordRepository;
    @Autowired
    private AdUnitItRepository unitItRepository;
    @Autowired
    private AdUnitDistrictRepository unitDistrictRepository;

    @Test
    public void dumpAdTableData() {
        dumpAdPlanTable(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN)
        );
        dumpAdUnitTable(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT)
        );
        dumpAdCreativeTable(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE)
        );

        dumpAdCreativeUnitTable(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT)
        );

        dumpAdUnitKeywordTable(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD)
        );

        dumpAdUnitItTable(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT)
        );
        dumpAdUnitDistrictTable(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT)
        );
    }


    public void dumpAdPlanTable(String fileName) {
        List<AdPlan> adPlans = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adPlans)) {
            return;
        }
        List<AdPlanTable> adPlanTables = new ArrayList<>();
        adPlans.forEach(adPlan -> adPlanTables.add(
                new AdPlanTable(adPlan.getId(), adPlan.getUserId(),
                        adPlan.getPlanStatus(), adPlan.getStartDate(), adPlan.getEndDate())
        ));
        writeTableToFile(adPlanTables, fileName);
    }

    public void dumpAdUnitTable(String fileName) {
        List<AdUnit> adUnits = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adUnits)) {
            return;
        }
        List<AdUnitTable> adUnitTables = new ArrayList<>();
        adUnits.forEach(adUnit -> adUnitTables.add(
                new AdUnitTable(adUnit.getId(), adUnit.getUnitStatus(), adUnit.getPositionType(), adUnit.getPlanId())
        ));

        writeTableToFile(adUnitTables, fileName);
    }

    public void dumpAdCreativeTable(String fileName) {
        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }
        List<AdCreativeTable> adCreativeTables = new ArrayList<>();
        creatives.forEach(creative -> adCreativeTables.add(
                new AdCreativeTable(creative.getId(), creative.getName(), creative.getType(),
                        creative.getMaterialType(), creative.getWidth(), creative.getHeight(),
                        creative.getAuditStatus(), creative.getUrl())
        ));

        writeTableToFile(adCreativeTables, fileName);
    }

    public void dumpAdCreativeUnitTable(String fileName) {
        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }
        List<AdCreativeUnitTable> adCreativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(creativeUnit -> adCreativeUnitTables.add(
                new AdCreativeUnitTable(creativeUnit.getCreativeId(), creativeUnit.getUnitId())
        ));

        writeTableToFile(adCreativeUnitTables, fileName);

    }

    public void dumpAdUnitKeywordTable(String fileName) {
        List<AdUnitKeyword> adUnitKeywords = unitKeywordRepository.findAll();
        if (CollectionUtils.isEmpty(adUnitKeywords)) {
            return;
        }
        List<AdUnitKeywordTable> adUnitKeywordTables = new ArrayList<>();
        adUnitKeywords.forEach(adUnitKeyword -> adUnitKeywordTables.add(
                new AdUnitKeywordTable(adUnitKeyword.getUnitId(), adUnitKeyword.getKeyword())
        ));

        writeTableToFile(adUnitKeywordTables, fileName);
    }

    public void dumpAdUnitItTable(String fileName) {
        List<AdUnitIt> adUnitIts = unitItRepository.findAll();
        if (CollectionUtils.isEmpty(adUnitIts)) {
            return;
        }
        List<AdUnitItTable> adUnitItTables = new ArrayList<>();
        adUnitIts.forEach(adUnitIt -> adUnitItTables.add(
                new AdUnitItTable(adUnitIt.getUnitId(), adUnitIt.getItTag())
        ));

        writeTableToFile(adUnitItTables, fileName);
    }

    public void dumpAdUnitDistrictTable(String fileName) {
        List<AdUnitDistrict> adUnitDistricts = unitDistrictRepository.findAll();
        if (CollectionUtils.isEmpty(adUnitDistricts)) {
            return;
        }
        List<AdUnitDistrictTable> adUnitDistrictTables = new ArrayList<>();
        adUnitDistricts.forEach(adUnitDistrict -> adUnitDistrictTables.add(
                new AdUnitDistrictTable(adUnitDistrict.getUnitId(),
                        adUnitDistrict.getProvince(), adUnitDistrict.getCity())
        ));

        writeTableToFile(adUnitDistrictTables, fileName);
    }

    private <T> void writeTableToFile(List<T> objects, String fileName) {

        Path path = Paths.get(fileName);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (T table : objects) {
                bw.write(JSON.toJSONString(table));
                bw.newLine();
            }
        } catch (IOException e) {
            log.error("writeTableToFile error");
        }
    }
}
