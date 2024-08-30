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
import com.system.library.application.filter.PatronFilter;
import com.system.library.application.model.request.PatronReqModel;
import com.system.library.application.model.response.PatronResModel;
import com.system.library.application.service.PatronService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

	@Autowired
	PatronService patronService;

	@Autowired
	ObjectMapper objectMapper;

	@Operation(summary = "Retrieve a List of All Books")
	@GetMapping
	private ResponseEntity<List<PatronResModel>> getAllPatrons() {
		return new ResponseEntity<>(patronService.getAllPatrons(), HttpStatus.OK);
	}

	@Operation(summary = "Retrieve a List of All Filtered Books")
	@GetMapping("/filter")
	private ResponseEntity<List<PatronResModel>> getAllFilteredPatrons(
			@Parameter(name = "Page Size") @RequestParam(defaultValue = "5") int pageSize,
			@Parameter(name = "Page Index") @RequestParam(defaultValue = "0") int pageIndex,
			@Parameter(name = "Sort Field") @RequestParam(required = false) String sortField,
			@Parameter(name = "Sort Order") @RequestParam(required = false, defaultValue = "asc") String sortOrder,
			@Parameter(name = "Patron Filter", example = ""
					+ "Before Encodeing : (filter = {{\"name\" : null, \"email\": null, \"phone\": null}})\\n "
					+ "After Encodeing  : (filter = %7B%22name%22%20%3A%20null%2C%20%22email%22%3A%20null%2C%20%22phone%22%3A%20null%7D)"
					+ "") @RequestParam(required = false) String filter)
			throws JsonProcessingException {
		PatronFilter patronFilter = new PatronFilter();
		if (filter != null && !filter.equalsIgnoreCase("")) {
			patronFilter = objectMapper.readValue(filter, PatronFilter.class);
		}
		return new ResponseEntity<>(
				patronService.getAllFilteredPatrons(patronFilter, pageSize, pageIndex, sortField, sortOrder),
				HttpStatus.OK);
	}

	@Operation(summary = "Retrieve Details of Specific Patron By ID")
	@GetMapping("/{id}")
	private ResponseEntity<PatronResModel> getPatronById(
			@Parameter(name = "Patron ID") @PathVariable("id") int patronId) {
		return new ResponseEntity<>(patronService.getPatronById(patronId), HttpStatus.OK);
	}

	@Operation(summary = "Add a New Patron to the System")
	@PostMapping
	private ResponseEntity<PatronResModel> createPatron(
			@Parameter(name = "Patron Request Model") @Valid @RequestBody PatronReqModel patronReqModel) {
		return new ResponseEntity<>(patronService.createPatron(patronReqModel), HttpStatus.OK);
	}

	@Operation(summary = "Update an Existing Patron's Information")
	@PutMapping("/{id}")
	private ResponseEntity<PatronResModel> updatePatronById(
			@Parameter(name = "Patron ID") @PathVariable("id") int patronId,
			@Parameter(name = "Patron Request Model") @Valid @RequestBody PatronReqModel patronReqModel) {
		return new ResponseEntity<>(patronService.updatePatronById(patronId, patronReqModel), HttpStatus.OK);
	}

	@Operation(summary = "Remove Patron from the System")
	@DeleteMapping("/{id}")
	private ResponseEntity<Void> deletePatronById(@Parameter(name = "Patron ID") @PathVariable("id") int patronId) {
		return new ResponseEntity<>(patronService.deletePatronById(patronId), HttpStatus.OK);
	}
}
