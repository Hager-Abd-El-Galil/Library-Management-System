package com.system.library.application.model.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class BookResModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4253538138645390497L;

	private int id;
	
	private String title;
	
	private String author;
	
	private int publicationYear;
	
	private String isbn;
}
