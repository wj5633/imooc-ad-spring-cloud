package com.wj5633.ad.index.adunit;

import com.wj5633.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/1 23:12
 * @description
 */

@Slf4j
@Component
public class AdUnitIndex implements IndexAware<Long, AdUnitObject> {

    private static Map<Long, AdUnitObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    public Set<Long> match(Integer positionType) {
        Set<Long> adUnitIds = new HashSet<>();

        objectMap.forEach((k, v) -> {
            if (AdUnitObject.isAdSlotTypeOK(positionType, v.getPositionType())) {
                adUnitIds.add(k);
            }
        });
        return adUnitIds;
    }

    public List<AdUnitObject> fetch(Collection<Long> adUnitIds) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return Collections.emptyList();
        }
        List<AdUnitObject> result = new ArrayList<>();

        adUnitIds.forEach(id -> {
            AdUnitObject object = get(id);
            if (object == null) {
                log.error("AdUnitObject not found: {}", id);
                return;
            }
            result.add(object);
        });

        return result;
    }

    @Override
    public AdUnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdUnitObject value) {
        log.info("AdUnitIndex, before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("AdUnitIndex, after add: {}", objectMap);
    }

    @Override
    public void update(Long key, AdUnitObject value) {
        log.info("AdUnitIndex, before update: {}", objectMap);
        AdUnitObject oldObject = objectMap.get(key);
        if (oldObject == null) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        objectMap.put(key, value);
        log.info("AdUnitIndex, after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, AdUnitObject value) {
        log.info("AdUnitIndex, before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("AdUnitIndex, after delete: {}", objectMap);
    }
}
