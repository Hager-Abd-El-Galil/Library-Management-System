package com.system.library.application.model.request;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ContactInfoReqModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2346077517837071676L;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Pattern(regexp = "^[0-9]{11}$", message = "phone must be exactly 11 digits")
	private String phone;

	@Valid
	private AddressReqModel address;
}
