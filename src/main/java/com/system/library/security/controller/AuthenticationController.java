package com.system.library.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.system.library.security.config.JwtUtil;
import com.system.library.security.model.request.LoginReqModel;
import com.system.library.security.model.response.LoginResModel;
import com.system.library.security.service.impl.DefaultUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private DefaultUserService defaultUserService;

	@Operation(summary = "Login")
	@PostMapping("/login")
	public LoginResModel createToken(@Parameter(name = "Login Request Model") @RequestBody LoginReqModel loginReqModel)
			throws Exception {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginReqModel.getUsername(), loginReqModel.getPassword()));

		if (authentication.isAuthenticated()) {
			return new LoginResModel(
					jwtUtil.generateToken(defaultUserService.loadUserByUsername(loginReqModel.getUsername())));
		} else {
			throw new Exception("Invalid Username Or Password");
		}

	}
}
