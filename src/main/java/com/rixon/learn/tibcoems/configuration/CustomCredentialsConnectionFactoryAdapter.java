package com.rixon.learn.tibcoems.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.stereotype.Component;

import javax.jms.*;

public class CustomCredentialsConnectionFactoryAdapter extends UserCredentialsConnectionFactoryAdapter {

    private CredentialsFetcher credentialsFetcher;

    public void setCredentialsFetcher(CredentialsFetcher credentialsFetcher) {
        this.credentialsFetcher = credentialsFetcher;
    }

    public CustomCredentialsConnectionFactoryAdapter() {
        super();
    }

    @Override
    public void setTargetConnectionFactory(ConnectionFactory targetConnectionFactory) {
        super.setTargetConnectionFactory(targetConnectionFactory);
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }

    @Override
    public void setCredentialsForCurrentThread(String username, String password) {
        CustomJmsUserCredentials updatedCredentials = updateCredentials(username, password);
        super.setCredentialsForCurrentThread(updatedCredentials.username, updatedCredentials.password);
    }

    @Override
    public void removeCredentialsFromCurrentThread() {
        super.removeCredentialsFromCurrentThread();
    }

    @Override
    public Connection createConnection(String username, String password) throws JMSException {
        CustomJmsUserCredentials updatedCredentials = updateCredentials(username, password);
        return super.createConnection(updatedCredentials.username, updatedCredentials.password);
    }

    @Override
    protected Connection doCreateConnection(String username, String password) throws JMSException {
        CustomJmsUserCredentials updatedCredentials = updateCredentials(username, password);
        return super.doCreateConnection(updatedCredentials.username, updatedCredentials.password);
    }

    @Override
    public QueueConnection createQueueConnection(String username, String password) throws JMSException {
        CustomJmsUserCredentials updatedCredentials = updateCredentials(username, password);
        return super.createQueueConnection(updatedCredentials.username, updatedCredentials.password);
    }

    @Override
    protected QueueConnection doCreateQueueConnection(String username, String password) throws JMSException {
        CustomJmsUserCredentials updatedCredentials = updateCredentials(username, password);
        return super.doCreateQueueConnection(updatedCredentials.username, updatedCredentials.password);
    }

    @Override
    public TopicConnection createTopicConnection(String username, String password) throws JMSException {
        CustomJmsUserCredentials updatedCredentials = updateCredentials(username, password);
        return super.createTopicConnection(updatedCredentials.username, updatedCredentials.password);
    }

    @Override
    protected TopicConnection doCreateTopicConnection(String username, String password) throws JMSException {
        CustomJmsUserCredentials updatedCredentials = updateCredentials(username, password);
        return super.doCreateTopicConnection(updatedCredentials.username, updatedCredentials.password);
    }

    @Override
    public JMSContext createContext() {
        return super.createContext();
    }

    @Override
    public JMSContext createContext(String userName, String password) {
        CustomJmsUserCredentials updatedCredentials = updateCredentials(userName, password);
        return super.createContext(updatedCredentials.username, updatedCredentials.password);
    }

    @Override
    public JMSContext createContext(String userName, String password, int sessionMode) {
        CustomJmsUserCredentials updatedCredentials = updateCredentials(userName, password);
        return super.createContext(updatedCredentials.username, updatedCredentials.password, sessionMode);
    }

    @Override
    public JMSContext createContext(int sessionMode) {
        return super.createContext(sessionMode);
    }

    private CustomJmsUserCredentials updateCredentials(String username, String password) {
        String updatedUser = credentialsFetcher.getUsername(username);
        String updatedPassword = credentialsFetcher.getPassword(password);
        super.removeCredentialsFromCurrentThread();
        super.setCredentialsForCurrentThread(updatedUser,updatedPassword);
        return new CustomJmsUserCredentials(updatedUser,updatedPassword);

    }

    private static final class CustomJmsUserCredentials {

        public final String username;

        public final String password;

        public CustomJmsUserCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public String toString() {
            return "CustomJmsUserCredentials[username='" + this.username + "',password='*****************']";
        }
    }
}
