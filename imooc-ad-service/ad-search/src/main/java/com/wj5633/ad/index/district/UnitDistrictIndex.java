package com.wj5633.ad.index.district;

import com.wj5633.ad.index.IndexAware;
import com.wj5633.ad.search.vo.feature.DistrictFeature;
import com.wj5633.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/2 0:00
 * @description
 */

@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> districtUnitMap;
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitDistrictIndex, before add: {}", districtUnitMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
                key, districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getorCreate(
                    unitId, unitDistrictMap,
                    ConcurrentSkipListSet::new
            );
            districts.add(key);
        }
        log.info("UnitDistrictIndex, after add: {}", districtUnitMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("UnitDistrictIndex not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitDistrictIndex, before delete: {}", districtUnitMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
                key, districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);
        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getorCreate(
                    unitId, unitDistrictMap,
                    ConcurrentSkipListSet::new
            );
            districts.remove(key);
        }

        log.info("UnitDistrictIndex, after delete: {}", districtUnitMap);
    }

    public boolean match(Long unitId, List<DistrictFeature.ProvinceAndCity> districts) {
        if (unitDistrictMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitDistrictMap.get(unitId))) {
            Set<String> unitDistricts = unitDistrictMap.get(unitId);
            List<String> targetDistricts = districts.stream()
                    .map(d -> CommonUtils.stringConcat(d.getProvince(), d.getCity()))
                    .collect(Collectors.toList());
            return CollectionUtils.isSubCollection(targetDistricts, unitDistricts);
        }
        return false;
    }
}
