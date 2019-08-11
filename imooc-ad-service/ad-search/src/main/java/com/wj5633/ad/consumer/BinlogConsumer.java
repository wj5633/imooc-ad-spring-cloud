package com.wj5633.ad.consumer;

import com.alibaba.fastjson.JSON;
import com.wj5633.ad.dto.MySqlRowData;
import com.wj5633.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created at 2019/8/11 16:36.
 *
 * @author wangjie
 * @version 1.0.0
 */

@Slf4j
@Component
public class BinlogConsumer {

    private final ISender sender;

    public BinlogConsumer(ISender sender) {
        this.sender = sender;
    }

    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(
                    message.toString(),
                    MySqlRowData.class
            );
            log.info("kafka processMysqlRowData: {}", JSON.toJSONString(rowData));
            sender.sender(rowData);
        }
    }
}
