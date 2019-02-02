package com.wj5633.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/2/1 23:31
 * @description
 */
public class CommonUtils {

    public static <K, V> V getorCreate(K key, Map<K, V> map, Supplier<V> factory) {

        return map.computeIfAbsent(key, k -> factory.get());
    }
}
