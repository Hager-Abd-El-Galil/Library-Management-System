package com.system.library.application.model.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class AddressResModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6468066632177674128L;

	private String street;
	
    private String city;
    
    private String state;
    
    private String zipCode;
}
