package com.system.library.application.model.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BookReqModel implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 9022882492030629408L;

	@NotNull
	@Size(max = 150)
	private String title;

	@NotNull
	@Size(max = 150)
	private String author;

	@NotNull
	private int publicationYear;

	@NotNull
	@Pattern(regexp = "\\d{13}", message = "isbn must be exactly 13 digits")
	private String isbn;
}
