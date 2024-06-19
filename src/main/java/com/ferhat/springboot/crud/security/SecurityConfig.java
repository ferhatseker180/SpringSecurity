package com.ferhat.springboot.crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    // This is the most basic method of adding users and user roles for auth.
    //However, there is no database connection in this method, it is a very basic structure and is added manually.
    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails ferhat = User.builder()
                .username("ferhat")
                .password("{noop}123456789")
                .roles("ADMIN", "MANAGER", "EMPLOYEE")
                .build();

        UserDetails ali = User.builder()
                .username("ali")
                .password("{noop}1234")
                .roles("MANAGER", "EMPLOYEE")
                .build();

        UserDetails cemre = User.builder()
                .username("cemre")
                .password("{noop}12")
                .roles("EMPLOYEE")
                .build();

        return new InMemoryUserDetailsManager(ferhat, ali, cemre);
    }

     */

    // We are trying a second and slightly more advanced method of auth.
    // This method is connected to the database and performs auth operations based on database data, but does not yet have any advanced encryption logic.
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/employee").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/employee/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/employee").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/employee").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/employee/**").hasRole("ADMIN")
        );
        // Use HTT Basic Authentication
        http.httpBasic(Customizer.withDefaults());

        // Disable Cross Site Request Forgery (CSRF)
        http.csrf(csrf -> csrf.disable());
        return http.build();

    }
}
