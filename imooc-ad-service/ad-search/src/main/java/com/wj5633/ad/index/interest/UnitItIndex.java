package com.wj5633.ad.index.interest;

import com.wj5633.ad.index.IndexAware;
import com.wj5633.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/1 23:46
 * @description
 */

@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> itUnitMap;
    private static Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitItIndex, before add: {}", itUnitMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
                key, itUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> its = CommonUtils.getorCreate(
                    unitId, unitItMap,
                    ConcurrentSkipListSet::new
            );
            its.add(key);
        }
        log.info("UnitItIndex, after add: {}", itUnitMap);
    }


    @Override
    public void update(String key, Set<Long> value) {
        log.error("UnitItIndex not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitItIndex, before delete: {}", itUnitMap);

        Set<Long> unitIds = CommonUtils.getorCreate(
                key, itUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);
        for (Long unitId : value) {
            Set<String> its = CommonUtils.getorCreate(
                    unitId, unitItMap,
                    ConcurrentSkipListSet::new
            );
            its.remove(key);
        }

        log.info("UnitItIndex, after delete: {}", itUnitMap);
    }

    public boolean match(Long unitId, List<String> itTags) {
        if (unitItMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitItMap.get(unitId))) {
            return CollectionUtils.isSubCollection(itTags, unitItMap.get(unitId));
        }
        return false;
    }
}
