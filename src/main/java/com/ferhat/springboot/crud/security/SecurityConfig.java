package com.ferhat.springboot.crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails ferhat = User.builder()
                .username("ferhat")
                .password("{noop}123456789")
                .roles("ADMIN","MANAGER","EMPLOYEE")
                .build();

        UserDetails ali = User.builder()
                .username("ali")
                .password("{noop}1234")
                .roles("MANAGER","EMPLOYEE")
                .build();

        UserDetails cemre = User.builder()
                .username("cemre")
                .password("{noop}12")
                .roles("EMPLOYEE")
                .build();

        return new InMemoryUserDetailsManager(ferhat, ali, cemre);
    }
}
