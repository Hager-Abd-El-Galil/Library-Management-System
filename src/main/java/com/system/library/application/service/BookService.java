package com.system.library.application.service;

import java.util.List;

import javax.validation.Valid;

import com.system.library.application.filter.BookFilter;
import com.system.library.application.model.request.BookReqModel;
import com.system.library.application.model.response.BookResModel;

public interface BookService {

	List<BookResModel> getAllBooks();

	List<BookResModel> getAllFilteredBooks(BookFilter bookFilter, int pageSize, int pageIndex, String sortField,
			String sortOrder);

	BookResModel createBook(@Valid BookReqModel bookReqModel);

	BookResModel getBookById(int id);

	BookResModel updateBookById(int id, @Valid BookReqModel bookReqModel);

	Void deleteBookById(int id);
}
