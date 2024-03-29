package com.rixon.learn.tibcoems.configuration;

import com.tibco.tibjms.TibjmsConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;
import java.util.Collections;
import java.util.Map;

@Configuration
public class TibcoConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(TibcoConfiguration.class);

    @Value("${tibco.url}")
    private String url;

    @Value("${tibco.user}")
    private String user;

    @Value("${tibco.password}")
    private String password;


    @Bean
    public CredentialsFetcher credentialsFetcher() {
        return new CredentialsFetcher(user,password);
    }

    @Bean
    public CustomCredentialsConnectionFactoryAdapter customCredentialsConnectionFactoryAdapter(CredentialsFetcher credentialsFetcher) {
        CustomCredentialsConnectionFactoryAdapter customCredentialsConnectionFactoryAdapter = new CustomCredentialsConnectionFactoryAdapter();
        customCredentialsConnectionFactoryAdapter.setCredentialsFetcher(credentialsFetcher);
        TibjmsConnectionFactory tibjmsConnectionFactory = new TibjmsConnectionFactory(url,null,emsProperties());
        customCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(tibjmsConnectionFactory);
        return customCredentialsConnectionFactoryAdapter;

    }

    @Bean
    public ConnectionFactory connectionFactory(CustomCredentialsConnectionFactoryAdapter customCredentialsConnectionFactoryAdapter) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(customCredentialsConnectionFactoryAdapter);
        cachingConnectionFactory.setCacheConsumers(true);
        cachingConnectionFactory.setCacheProducers(true);
        cachingConnectionFactory.setSessionCacheSize(10);
        return cachingConnectionFactory;

    }

    private Map<String,String> emsProperties() {
        return Collections.emptyMap();
    }


    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
//        factory.setTransactionManager(transactionManager);
        factory.setSessionTransacted(true);
        factory.setErrorHandler(throwable -> LOGGER.error("Error creating new message listener factory",throwable));
        factory.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONSUMER);
        return factory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactoryPubSub(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
//        factory.setTransactionManager(transactionManager);
        factory.setSessionTransacted(true);
        factory.setPubSubDomain(true);
        factory.setErrorHandler(throwable -> LOGGER.error("Error creating new message listener factory for topics",throwable));
        factory.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONSUMER);
        return factory;
    }

}
