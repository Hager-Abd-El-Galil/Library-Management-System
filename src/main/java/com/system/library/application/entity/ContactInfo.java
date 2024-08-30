package com.system.library.application.entity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Embeddable
public class ContactInfo {

	@NotNull
	@Email
	@Size(max = 50)
	private String email;

	@NotNull
	@Pattern(regexp = "^[0-9]{11}$")
	private String phone;

	@Embedded
	private Address address;
}
