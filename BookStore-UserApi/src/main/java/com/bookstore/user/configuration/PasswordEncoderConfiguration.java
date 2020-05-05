package com.bookstore.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*****************************************************************************************************
 * Password EncryptionEncoder Configuration Class Which Inject Object Of PasswordEncoderConfiguration.
 *  
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-11
 * 
 ******************************************************************************************************/
@Configuration
public class PasswordEncoderConfiguration {

	@Bean
    public BCryptPasswordEncoder  passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
