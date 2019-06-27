package com.wj5633.ad.mysql.dto;

import com.wj5633.ad.mysql.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午12:57
 * @description
 */

@Data
public class ParseTemplate {

    private String database;
    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    public static ParseTemplate parse(Template _template) {
        ParseTemplate parseTemplate = new ParseTemplate();
        parseTemplate.setDatabase(_template.getDatabase());

        for (JsonTable table : _template.getTableList()) {
            String name = table.getTableName();
            Integer level = table.getLevel();

            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            tableTemplate.setLevel(level.toString());
            parseTemplate.tableTemplateMap.put(name, tableTemplate);

            Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();

            for (JsonTable.Column column : table.getInsert()) {
                getAndCreateIfNeed(
                        OpType.ADD,
                        opTypeFieldSetMap,
                        ArrayList::new)
                        .add(column.getColumn());

                opTypeFieldSetMap.computeIfAbsent(OpType.ADD, k -> new ArrayList<>())
                        .add(column.getColumn());
            }

            for (JsonTable.Column column : table.getUpdate()) {
                opTypeFieldSetMap.computeIfAbsent(OpType.UPDATE, k -> new ArrayList<>())
                        .add(column.getColumn());
            }

            for (JsonTable.Column column : table.getDelete()) {

                opTypeFieldSetMap.computeIfAbsent(OpType.DELETE, k -> new ArrayList<>())
                        .add(column.getColumn());
            }
        }

        return parseTemplate;
    }

    private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map, Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
