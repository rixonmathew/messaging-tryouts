package com.rixon.learn.tibcoems.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CredentialsFetcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsFetcher.class);

    private final String username;
    private final String password;

    public CredentialsFetcher(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername(String username) {
        LOGGER.info("Returning custom username");
        return this.username;
    }

    public String getPassword(String password) {
        LOGGER.info("Returning custom password with check to see if has changed");
        if (password!=null && !password.equals("tibco")) {
            LOGGER.warn("Password has been changed providing the new password");
        }
        return this.password;
    }

    public String getPassword() {
        LOGGER.info("Returning custom password");
        return this.password;
    }


}
