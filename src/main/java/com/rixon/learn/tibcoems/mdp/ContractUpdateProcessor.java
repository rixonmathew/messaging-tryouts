package com.rixon.learn.tibcoems.mdp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

import static com.rixon.learn.tibcoems.util.Constants.CONTRACT_BROADCAST;
import static com.rixon.learn.tibcoems.util.Constants.CONTRACT_UPDATES;

@Component
public class ContractUpdateProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(ContractUpdateProcessor.class);

    @JmsListener(containerFactory = "jmsListenerContainerFactory",
                 destination = CONTRACT_UPDATES,
                 concurrency = "10-20"
    )
    public void processContractUpdates(Message message) throws JMSException {
        LOGGER.info("Got message [{}] from queue",message.getBody(String.class));
    }

    @JmsListener(containerFactory = "jmsListenerContainerFactoryPubSub",
                 destination = CONTRACT_BROADCAST,
                 concurrency = "10-20"
    )
    public void processContractUpdatesFromTopic(Message message) throws JMSException {
        LOGGER.info("Got message [{}] from topic",message.getBody(String.class));
    }
}
