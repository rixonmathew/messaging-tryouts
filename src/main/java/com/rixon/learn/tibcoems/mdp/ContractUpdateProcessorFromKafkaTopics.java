package com.rixon.learn.tibcoems.mdp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.rixon.learn.tibcoems.util.Constants.CONTRACT_UPDATES_KAFKA_TOPIC;
import static com.rixon.learn.tibcoems.util.Constants.KAFKA_GROUP_ID;

@KafkaListener(topics = CONTRACT_UPDATES_KAFKA_TOPIC,groupId = KAFKA_GROUP_ID)
@Component
public class ContractUpdateProcessorFromKafkaTopics {

    private final static Logger LOGGER = LoggerFactory.getLogger(ContractUpdateProcessorFromKafkaTopics.class);

    @KafkaHandler(isDefault = true)
    public void listen(String message) {
        LOGGER.info("Received message [{}] from kafka topic", message);
    }


    @KafkaHandler
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        LOGGER.info("Received message [{}] from kafka topic and partition [{}]", message, partition);

    }

}
