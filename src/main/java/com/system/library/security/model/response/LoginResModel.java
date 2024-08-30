package com.system.library.security.model.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5139971405075269614L;

	private String accessToken;
}
