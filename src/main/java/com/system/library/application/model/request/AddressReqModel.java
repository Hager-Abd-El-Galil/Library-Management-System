package com.system.library.application.model.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AddressReqModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3733121402285643594L;

	@NotNull
	@Size(max = 50)
	private String street;

	@NotNull
	@Size(max = 50)
	private String city;

	@NotNull
	@Size(max = 50)
	private String state;

	@NotNull
	@Pattern(regexp = "\\d{5}", message = "ZIP Code must be exactly 5 digits")
	private String zipCode;
}
