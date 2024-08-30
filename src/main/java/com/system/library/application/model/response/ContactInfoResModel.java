package com.system.library.application.model.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContactInfoResModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221763286879014L;

	private String email;

	private String phone;

	private AddressResModel address;
}
