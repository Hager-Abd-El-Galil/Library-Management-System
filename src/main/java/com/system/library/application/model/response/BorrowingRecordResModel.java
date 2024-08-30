package com.system.library.application.model.response;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class BorrowingRecordResModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -348321519882375904L;

	private int id;

	private LocalDate borrowingDate;

	private LocalDate returnDate;

	private PatronResModel patron;

	private BookResModel book;
}
