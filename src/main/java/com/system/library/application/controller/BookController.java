package com.system.library.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.library.application.filter.BookFilter;
import com.system.library.application.model.request.BookReqModel;
import com.system.library.application.model.response.BookResModel;
import com.system.library.application.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	ObjectMapper objectMapper;

	@Operation(summary = "Retrieve a List of All Books")
	@GetMapping
	private ResponseEntity<List<BookResModel>> getAllBooks() {
		return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
	}

	@Operation(summary = "Retrieve a List of All Filtered Books")
	@GetMapping("/filter")
	private ResponseEntity<List<BookResModel>> getAllFilteredBooks(
			@Parameter(name = "Page Size") @RequestParam(defaultValue = "5") int pageSize,
			@Parameter(name = "Page Index") @RequestParam(defaultValue = "0") int pageIndex,
			@Parameter(name = "Sort Field") @RequestParam(required = false) String sortField,
			@Parameter(name = "Sort Order") @RequestParam(required = false, defaultValue = "asc") String sortOrder,
			@Parameter(name = "Book Filter", example = ""
					+ "Before Encodeing : (filter = {\"title\" : null, \"author\": null, \"publicationYear\": 1949, \"isbn\": null})\\n "
					+ "After Encodeing  : (filter = %7B%22title%22%20%3A%20null%2C%20%22author%22%3A%20null%2C%20%22publicationYear%22%3A%201949%2C%20%22isbn%22%3A%20null%7D)"
					+ "") @RequestParam(required = false) String filter)
			throws JsonProcessingException {
		BookFilter bookFilter = new BookFilter();
		if (filter != null && !filter.equalsIgnoreCase("")) {
			bookFilter = objectMapper.readValue(filter, BookFilter.class);
		}
		return new ResponseEntity<>(
				bookService.getAllFilteredBooks(bookFilter, pageSize, pageIndex, sortField, sortOrder), HttpStatus.OK);
	}

	@Operation(summary = "Retrieve Details of Specific Book By ID")
	@GetMapping("/{id}")
	private ResponseEntity<BookResModel> getBookById(@Parameter(name = "Book ID") @PathVariable("id") int bookId) {
		return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
	}

	@Operation(summary = "Add a New Book to the Library")
	@PostMapping
	private ResponseEntity<BookResModel> createBook(
			@Parameter(name = "Book Request Model") @Valid @RequestBody BookReqModel bookReqModel) {
		return new ResponseEntity<>(bookService.createBook(bookReqModel), HttpStatus.OK);
	}

	@Operation(summary = "Update an Existing Book's Information")
	@PutMapping("/{id}")
	private ResponseEntity<BookResModel> updateBookById(@Parameter(name = "Book ID") @PathVariable("id") int bookId,
			@Parameter(name = "Book request Model") @Valid @RequestBody BookReqModel bookReqModel) {
		return new ResponseEntity<>(bookService.updateBookById(bookId, bookReqModel), HttpStatus.OK);
	}

	@Operation(summary = "Remove Book from the Library")
	@DeleteMapping("/{id}")
	private ResponseEntity<Void> deleteBookById(@Parameter(name = "Book ID") @PathVariable("id") int bookId) {
		return new ResponseEntity<>(bookService.deleteBookById(bookId), HttpStatus.OK);
	}
}
