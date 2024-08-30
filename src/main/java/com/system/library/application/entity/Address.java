package com.system.library.application.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Embeddable
public class Address {

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
	@Pattern(regexp = "\\d{5}")
	private String zipCode;
}
