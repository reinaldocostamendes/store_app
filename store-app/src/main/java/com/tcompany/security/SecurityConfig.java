package com.tcompany.security;

import org.springframework.beans.factory.annotation.Autowired;


public class SecurityConfig {
	@Autowired
	private ImplUserDetailsService implUserDetailsService;
	private static final String[] SWAGGER_WHITELIST = {
			"/v2/api-docs",
			"/swagger-resources",
			"/swagger-resources/**",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/**"
	};
	private static final String[] WEB_LIST= {
			"/",
			"/**",
			"/login"
	};





}