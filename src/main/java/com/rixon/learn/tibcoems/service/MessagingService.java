package com.rixon.learn.tibcoems.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.rixon.learn.tibcoems.util.Constants.CONTRACT_UPDATES;
import static com.rixon.learn.tibcoems.util.Constants.CONTRACT_BROADCAST;
import static com.rixon.learn.tibcoems.util.Constants.CONTRACT_UPDATES_KAFKA_TOPIC;

@Service
public class MessagingService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessagingService.class);
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private JmsTemplate jmsTemplate;
    private JmsTemplate jmsTemplateTopic;

    @PostConstruct
    public void init() {
        jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplateTopic = new JmsTemplate(connectionFactory);
        jmsTemplateTopic.setPubSubDomain(true);
    }

    public Mono<String> publishContractsToEMSQueue(int count) {

        long startTime = System.currentTimeMillis();
        IntStream.rangeClosed(1, count)
                .parallel()
                .forEach(i -> {
                    jmsTemplate.send(CONTRACT_UPDATES, session -> {
                        Message message = session.createTextMessage("Testing message " + i);
                        message.setJMSCorrelationID(UUID.randomUUID().toString());
                        return message;
                    });
                });

        return Mono.just(String.format("Published [%d] contracts in time [%d] ms to EMS Queue [%s]", count, System.currentTimeMillis() - startTime,CONTRACT_UPDATES));
    }

    public Mono<String> publishContractsToEMSTopic(int count) {

        long startTime = System.currentTimeMillis();
        IntStream.rangeClosed(1, count)
                .parallel()
                .forEach(i -> {
                    jmsTemplateTopic.send(CONTRACT_BROADCAST, session -> {
                        Message message = session.createTextMessage("Testing message " + i);
                        message.setJMSCorrelationID(UUID.randomUUID().toString());
                        return message;
                    });
                });

        return Mono.just(String.format("Published [%d] contracts in time [%d] ms to EMS Topic [%s]", count, System.currentTimeMillis() - startTime,CONTRACT_BROADCAST));
    }

    public Mono<String> publishContractsToKafkaDestinations(int count) {
        long startTime = System.currentTimeMillis();
        IntStream.rangeClosed(1, count)
                .parallel()
                .forEach(i -> {

                    String key = "KEY-"+i;
                    String message ="Message to Kafka " +i;
                    ListenableFuture<SendResult<String, String>> future =
                            kafkaTemplate.send(CONTRACT_UPDATES_KAFKA_TOPIC, i%10,key,message);

                    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

                        @Override
                        public void onSuccess(SendResult<String, String> result) {
                            LOGGER.info("{}","Sent message=[" + message +
                                    "] with offset=[" + result.getRecordMetadata().offset() + "] and partition ["+result.getRecordMetadata().partition() +"]");
                        }
                        @Override
                        public void onFailure(Throwable ex) {
                            LOGGER.error("{} {}","Unable to send message=["
                                    + message + "] due to : " + ex.getMessage(),ex);
                        }
                    });

                });

        return Mono.just(String.format("Published [%d] contracts in time [%d] ms to Kafka Topic [%s]", count, System.currentTimeMillis() - startTime,CONTRACT_UPDATES_KAFKA_TOPIC));
    }

}
