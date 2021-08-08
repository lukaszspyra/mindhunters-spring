package com.spyrka.mindhunters.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
        http
                .authorizeRequests()
                .antMatchers("/","/list","/search", "/css/**", "/img/**", "/js/**", "/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();
    }
}