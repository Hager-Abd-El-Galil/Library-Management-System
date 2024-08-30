package com.system.library.application.model.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class PatronResModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5399520210795472772L;

	private int id;
	
	private String name;

	private ContactInfoResModel contactInfo;
}
