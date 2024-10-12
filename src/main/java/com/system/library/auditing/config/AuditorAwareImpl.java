package com.system.library.auditing.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.system.library.security.entity.UserCredentials;

@Configuration
public class AuditorAwareImpl implements AuditorAware<UserCredentials> {

	@Override
	public Optional<UserCredentials> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}

		return Optional.of((UserCredentials) authentication.getPrincipal());
	}

}
