package com.wj5633.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.wj5633.ad.dto.MySqlRowData;
import com.wj5633.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wangjie
 * @version 1.0.0
 * @create 19-2-12 下午4:18
 * @description
 */

@Slf4j
@Component()
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySqlRowData rowData) {
        log.info("binlog kafka service send MysqlRowData.");
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }
}
