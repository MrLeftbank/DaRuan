package com.sx.oesb.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Value("${password.encode.secret-key}")
    private String key;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new StandardPasswordEncoder(key);
    }
}
