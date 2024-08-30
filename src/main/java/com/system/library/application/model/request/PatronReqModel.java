package com.system.library.application.model.request;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PatronReqModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7078274037009036313L;

	@NotNull
	@NotBlank
	private String name;

	@NotNull
	@Valid
	private ContactInfoReqModel contactInfo;

}
