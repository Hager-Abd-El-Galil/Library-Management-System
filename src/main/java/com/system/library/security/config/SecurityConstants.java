package com.system.library.security.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConstants {

	@Value("${token.secret}")
	public String tokenSecretProperty;

	public static String TOKEN_SECRET;

	public static final long ACCESS_TOKEN_VALIDITY = 864000000; // 10 DAYS IN MILLI SECONDES

	public static final String TOKEN_PREFIX = "Bearer ";

	public static final String HEADER_STRING = "Authorization";

	@PostConstruct
	public void init() {
		TOKEN_SECRET = this.tokenSecretProperty;
	}
}
