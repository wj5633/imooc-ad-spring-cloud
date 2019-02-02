package com.wj5633.ad.index.creativeunit;

import com.wj5633.ad.index.IndexAware;
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
 * @create 2019/2/2 0:34
 * @description
 */

@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

    // <adId-unitId, CreativeUnitObject>
    private static Map<String, CreativeUnitObject> objectMap;
    private static Map<Long, Set<Long>> creativeUnitMap;
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        log.info("CreativeUnitIndex, before add: {}", objectMap);

        objectMap.put(key, value);

        Set<Long> unitIdSet = creativeUnitMap.get(value.getAdId());
        if (unitIdSet == null) {
            unitIdSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(), unitIdSet);
        }
        unitIdSet.add(value.getUnitId());

        Set<Long> creativeSet = creativeUnitMap.get(value.getUnitId());
        if (creativeSet == null) {
            creativeSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getUnitId(), creativeSet);
        }
        creativeSet.add(value.getAdId());

        log.info("CreativeUnitIndex, after add: {}", objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnitIndex not support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.info("CreativeUnitIndex, before delete: {}", objectMap);

        objectMap.remove(key);

        Set<Long> unitIdSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isNotEmpty(unitIdSet)) {
            unitIdSet.remove(value.getUnitId());
        }
        Set<Long> creativeSet = creativeUnitMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeSet)) {
            creativeSet.remove(value.getAdId());
        }
        log.info("CreativeUnitIndex, after delete: {}", objectMap);
    }

}
