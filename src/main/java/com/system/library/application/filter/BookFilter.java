package com.system.library.application.filter;

import lombok.Data;

@Data
public class BookFilter {

	private String title;

	private String author;

	private int publicationYear;

	private String isbn;
}
