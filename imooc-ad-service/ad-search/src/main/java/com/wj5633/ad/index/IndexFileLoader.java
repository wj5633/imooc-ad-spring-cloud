package com.wj5633.ad.index;

import com.alibaba.fastjson.JSON;
import com.wj5633.ad.dump.DConstant;
import com.wj5633.ad.dump.table.*;
import com.wj5633.ad.handler.AdLevelDatahandler;
import com.wj5633.ad.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/4 1:13
 * @description
 */

@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    @PostConstruct
    public void init() {
        List<String> adPlanStrings = loadDumpData(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN)
        );
        adPlanStrings.forEach(p -> AdLevelDatahandler.handleLevel2(
                JSON.parseObject(p, AdPlanTable.class),
                OpType.ADD
        ));

        List<String> adCreativeStrings = loadDumpData(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE)
        );
        adCreativeStrings.forEach(p -> AdLevelDatahandler.handleLevel2(
                JSON.parseObject(p, AdCreativeTable.class),
                OpType.ADD
        ));
        List<String> adUnitStrings = loadDumpData(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT)
        );
        adUnitStrings.forEach(u -> AdLevelDatahandler.handleLevel3(
                JSON.parseObject(u, AdUnitTable.class),
                OpType.ADD
        ));
        List<String> adCreativeUnitStrings = loadDumpData(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT)
        );
        adCreativeUnitStrings.forEach(cu -> AdLevelDatahandler.handleLevel3(
                JSON.parseObject(cu, AdCreativeUnitTable.class),
                OpType.ADD
        ));
        List<String> adUnitDistrictStrings = loadDumpData(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT)
        );
        adUnitDistrictStrings.forEach(d -> AdLevelDatahandler.handleLevel4(
                JSON.parseObject(d, AdUnitDistrictTable.class),
                OpType.ADD
        ));

        List<String> adUnitItStrings = loadDumpData(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT)
        );
        adUnitItStrings.forEach(it -> AdLevelDatahandler.handleLevel4(
                JSON.parseObject(it, AdUnitItTable.class),
                OpType.ADD
        ));
        List<String> AdUnitKeywordStrings = loadDumpData(
                String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD)
        );
        AdUnitKeywordStrings.forEach(kw -> AdLevelDatahandler.handleLevel4(
                JSON.parseObject(kw, AdUnitKeywordTable.class),
                OpType.ADD
        ));
    }

    private List<String> loadDumpData(String filename) {

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
