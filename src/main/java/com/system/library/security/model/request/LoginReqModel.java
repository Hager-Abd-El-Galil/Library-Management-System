package com.system.library.security.model.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginReqModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6873456245507493527L;

	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
