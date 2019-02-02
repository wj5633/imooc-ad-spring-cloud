package com.wj5633.ad.index.creative;

import com.wj5633.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/2 0:31
 * @description
 */

@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    private static Map<Long, CreativeObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        log.info("CreativeIndex, before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("CreativeIndex, after add: {}", objectMap);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("CreativeIndex, before update: {}", objectMap);
        CreativeObject oldObject = objectMap.get(key);
        if (oldObject == null) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        objectMap.put(key, value);
        log.info("CreativeIndex, after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("CreativeIndex, before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("CreativeIndex, after delete: {}", objectMap);
    }
}
