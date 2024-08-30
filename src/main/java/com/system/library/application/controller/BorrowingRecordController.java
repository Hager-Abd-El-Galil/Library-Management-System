package com.system.library.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.library.application.model.response.BorrowingRecordResModel;
import com.system.library.application.service.BorrowingRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {

	@Autowired
	BorrowingRecordService borrowingRecordService;

	@Operation(summary = "Allow a Patron to Borrow a Book")
	@PostMapping("/borrow/{bookId}/patron/{patronId}")
	private ResponseEntity<BorrowingRecordResModel> borrowBook(
			@Parameter(name = "Book ID") @PathVariable("bookId") int bookId,
			@Parameter(name = "Patron ID") @PathVariable("patronId") int patronId) {
		return new ResponseEntity<>(borrowingRecordService.borrowBook(bookId, patronId), HttpStatus.OK);
	}

	@Operation(summary = "Record the Return of Borrowed Book By a Patron")
	@PutMapping("/return/{bookId}/patron/{patronId}")
	private ResponseEntity<BorrowingRecordResModel> returnBook(
			@Parameter(name = "Book ID") @PathVariable("bookId") int bookId,
			@Parameter(name = "Patron ID") @PathVariable("patronId") int patronId) {
		return new ResponseEntity<>(borrowingRecordService.returnBook(bookId, patronId), HttpStatus.OK);
	}
}
