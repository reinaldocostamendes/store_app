package com.tcompany.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//@EnableWebSecurity
//@Configuration
	//@Order(1)
	public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private ImplUserDetailsService implUserDetailsService;
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
					.disable().authorizeRequests().antMatchers("/api/**")
					.authenticated().antMatchers("/index").permitAll()
					.and()
					.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
							UsernamePasswordAuthenticationFilter.class)
					.addFilterBefore(new JWTApiAuthFilter(), UsernamePasswordAuthenticationFilter.class);
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(implUserDetailsService)
					.passwordEncoder(new BCryptPasswordEncoder());
		}
	}
