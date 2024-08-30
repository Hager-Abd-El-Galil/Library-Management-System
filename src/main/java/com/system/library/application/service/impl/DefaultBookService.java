package com.system.library.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.system.library.application.entity.Book;
import com.system.library.application.filter.BookFilter;
import com.system.library.application.model.mapInterface.BookMapper;
import com.system.library.application.model.request.BookReqModel;
import com.system.library.application.model.response.BookResModel;
import com.system.library.application.repository.BookRepository;
import com.system.library.application.repository.BorrowingRecordRepository;
import com.system.library.application.service.BookService;
import com.system.library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.library.utils.exception.impl.BusinessLogicViolationException;

@Service
public class DefaultBookService implements BookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	BorrowingRecordRepository borrowingRecordRepository;

	@Autowired
	BookMapper bookMapper;

	@Override
	public List<BookResModel> getAllBooks() {
		return bookMapper.mapToBookResModel(bookRepository.findAll());
	}

	@Override
	public List<BookResModel> getAllFilteredBooks(BookFilter bookFilter, int pageSize, int pageIndex, String sortField,
			String sortOrder) {
		Pageable pageableRequest = null;

		if (sortField != null && !sortField.isBlank() && sortOrder != null && !sortOrder.isBlank()) {
			pageableRequest = PageRequest.of(pageIndex, pageSize,
					sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending()
							: Sort.by(sortField).descending());
		} else {
			pageableRequest = PageRequest.of(pageIndex, pageSize);
		}

		Page<Book> allBooks = bookRepository.findAllFilteredBooks(bookFilter, pageableRequest);

		if (allBooks.hasContent()) {
			return bookMapper.mapToBookResModel(allBooks.getContent());
		}

		return new ArrayList<>();
	}

	@Override
	public BookResModel createBook(BookReqModel bookReqModel) {

		validateBookReqModel(bookReqModel, 0);

		Book book = bookMapper.mapToBook(bookReqModel);
		bookRepository.save(book);

		return bookMapper.mapToBookResModel(book);
	}

	@Override
	public BookResModel getBookById(int id) {
		Book book = bookRepository.findById(id).orElse(null);

		if (book == null) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_NOT_FOUND.name());
		}

		return bookMapper.mapToBookResModel(book);
	}

	@Override
	public BookResModel updateBookById(int id, BookReqModel bookReqModel) {
		Book book = bookRepository.findById(id).orElse(null);

		if (book == null) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_NOT_FOUND.name());
		}

		validateBookReqModel(bookReqModel, id);

		bookRepository.save(bookMapper.mapBookReqModelToBook(bookReqModel, book));

		return bookMapper.mapToBookResModel(book);
	}

	@Override
	@Transactional
	public Void deleteBookById(int id) {
		Book book = bookRepository.findById(id).orElse(null);

		if (book == null) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_NOT_FOUND.name());
		}
		borrowingRecordRepository.deleteAllByBookId(id);
		bookRepository.deleteById(id);

		return null;
	}

	private void validateBookReqModel(BookReqModel bookReqModel, int bookId) {

		validateBookTitleAndAuthorUniqueness(bookReqModel, bookId);
		validateBookIsbnUniqueness(bookReqModel, bookId);

	}

	private void validateBookIsbnUniqueness(BookReqModel bookReqModel, int bookId) {
		Book existingBook = bookRepository.findByIsbn(bookReqModel.getIsbn());

		// Update
		if (bookId != 0) {
			if (existingBook != null && existingBook.getId() != bookId) {
				throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_ISBN_MUST_BE_UNIQUE.name());
			}

		} else { // Create
			if (existingBook != null) {
				throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_ISBN_MUST_BE_UNIQUE.name());
			}
		}

	}

	private void validateBookTitleAndAuthorUniqueness(BookReqModel bookReqModel, int bookId) {
		Book existingBook = bookRepository.findByTitleAndAuthor(bookReqModel.getTitle(), bookReqModel.getAuthor());

		// Update
		if (bookId != 0) {
			if (existingBook != null && existingBook.getId() != bookId) {
				throw new BusinessLogicViolationException(
						ApiErrorMessageEnum.BCV_BOOK_TITLE_AND_AUTHOR_MUST_BE_UNIQUE.name());
			}

		} else { // Create
			if (existingBook != null) {
				throw new BusinessLogicViolationException(
						ApiErrorMessageEnum.BCV_BOOK_TITLE_AND_AUTHOR_MUST_BE_UNIQUE.name());
			}
		}

	}

}
