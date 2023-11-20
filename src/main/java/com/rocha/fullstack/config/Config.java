package com.rocha.fullstack.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class Config {


    @Value("${stripe.api.secretKey}")
    private String secretKey;

    @PostConstruct
    public void initSecretKey(){
        Stripe.apiKey = secretKey;
    }


}
