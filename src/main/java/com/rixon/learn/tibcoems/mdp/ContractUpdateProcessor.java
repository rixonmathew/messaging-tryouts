package com.rixon.learn.tibcoems.mdp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;

import static com.rixon.learn.tibcoems.util.Constants.CONTRACT_UPDATES;

@Component
public class ContractUpdateProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(ContractUpdateProcessor.class);

    @JmsListener(containerFactory = "jmsListenerContainerFactory",
                 destination = CONTRACT_UPDATES,
                 concurrency = "10-20"
    )
    @Transactional(propagation = Propagation.REQUIRED,transactionManager = "transactionManager")
    public void processContractUpdates(Message message) throws JMSException {
        LOGGER.info("Got message [{}]",message.getBody(String.class));
    }
}
