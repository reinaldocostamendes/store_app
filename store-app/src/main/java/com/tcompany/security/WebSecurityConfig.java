package com.tcompany.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@EnableWebSecurity
@Configuration
//@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ImplUserDetailsService implUserDetailsService;

        @Override
       // @Order(2)
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/").permitAll()
                     .antMatchers(HttpMethod.GET,"/webjars/**").permitAll().
                    anyRequest().authenticated().and().formLogin().permitAll().
                    and().logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
            ;
        }
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(implUserDetailsService)
                    .passwordEncoder(new BCryptPasswordEncoder());
        }
    }

