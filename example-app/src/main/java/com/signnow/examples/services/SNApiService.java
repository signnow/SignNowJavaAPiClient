package com.signnow.examples.services;

import com.signnow.library.SNClient;
import com.signnow.library.SNClientBuilder;
import com.signnow.library.dto.User;
import com.signnow.library.exceptions.SNException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SNApiService {

    Logger logger = LoggerFactory.getLogger(SNApiService.class);

    @Value("${signnow.apiUrl}")
    private String apiUrl;

    @Value("${signnow.clientId}")
    private String clientId;

    @Value("${signnow.clientSecret}")
    private String clientSecret;

    @PostConstruct
    private void initClientBuilder() {
        SNClientBuilder.get(apiUrl, clientId, clientSecret);
    }

    public SNClient getSNClient(String email, String password) {
        try {
            return SNClientBuilder.get().getClientForExistingUser(email, password);
        } catch (SNException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public SNClient getSNClient(User snUser) {
        try {
            return SNClientBuilder.get().getClientForAuthenticatedUser(snUser);
        } catch (SNException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


    public void createNewSNUser(String email, String password) {
        try {
            SNClientBuilder.get().createUser(email, password);
        } catch (SNException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
