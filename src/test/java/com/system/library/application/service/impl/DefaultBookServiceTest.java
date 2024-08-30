package com.system.library.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.library.application.entity.Book;
import com.system.library.application.filter.BookFilter;
import com.system.library.application.model.mapInterface.BookMapper;
import com.system.library.application.model.request.BookReqModel;
import com.system.library.application.model.response.BookResModel;
import com.system.library.application.repository.BookRepository;
import com.system.library.application.repository.BorrowingRecordRepository;
import com.system.library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.library.utils.exception.impl.BusinessLogicViolationException;

@ExtendWith(MockitoExtension.class)
public class DefaultBookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private BorrowingRecordRepository borrowingRecordRepository;

	@Mock
	private BookMapper bookMapper;

	@InjectMocks
	private DefaultBookService bookService;

	private Book book;
	private BookReqModel bookReqModel;
	private BookResModel bookResModel;

	@BeforeEach
	public void setup() {
		book = new Book();
		book.setId(1);
		book.setTitle("Test Title");
		book.setAuthor("Test Author");
		book.setPublicationYear(2020);
		book.setIsbn("1234567890123");

		bookReqModel = new BookReqModel();
		bookReqModel.setTitle("Test Title");
		bookReqModel.setAuthor("Test Author");
		bookReqModel.setPublicationYear(2020);
		bookReqModel.setIsbn("1234567890123");

		bookResModel = new BookResModel();
		bookResModel.setId(1);
		bookResModel.setTitle("Test Title");
		bookResModel.setAuthor("Test Author");
		bookResModel.setPublicationYear(2020);
		bookResModel.setIsbn("1234567890123");
	}

	@Test
	public void testGetAllBooks() {
		List<Book> books = Arrays.asList(book);
		List<BookResModel> bookResModels = Arrays.asList(bookResModel);

		when(bookRepository.findAll()).thenReturn(books);
		when(bookMapper.mapToBookResModel(books)).thenReturn(bookResModels);

		List<BookResModel> result = bookService.getAllBooks();
		assertEquals(1, result.size());
		assertEquals("Test Title", result.get(0).getTitle());
	}

	@Test
	public void testGetAllFilteredBooks() {
		List<Book> books = Arrays.asList(book);
		List<BookResModel> bookResModels = Arrays.asList(bookResModel);
		Page<Book> page = new PageImpl<>(books);
		Pageable pageable = PageRequest.of(0, 5);

		when(bookRepository.findAllFilteredBooks(any(BookFilter.class), eq(pageable))).thenReturn(page);
		when(bookMapper.mapToBookResModel(books)).thenReturn(bookResModels);

		List<BookResModel> result = bookService.getAllFilteredBooks(new BookFilter(), 5, 0, "title", "asc");
		assertEquals(1, result.size());
		assertEquals("Test Title", result.get(0).getTitle());
	}

	@Test
	public void testCreateBook() {
		when(bookRepository.save(any(Book.class))).thenReturn(book);
		when(bookMapper.mapToBook(any(BookReqModel.class))).thenReturn(book);
		when(bookMapper.mapToBookResModel(book)).thenReturn(bookResModel);

		BookResModel result = bookService.createBook(bookReqModel);
		assertNotNull(result);
		assertEquals("Test Title", result.getTitle());
	}

	@Test
	public void testGetBookById_BookExists() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		when(bookMapper.mapToBookResModel(book)).thenReturn(bookResModel);

		BookResModel result = bookService.getBookById(1);
		assertNotNull(result);
		assertEquals("Test Title", result.getTitle());
	}

	@Test
	public void testGetBookById_BookNotExists() {
		when(bookRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> bookService.getBookById(1));
		assertEquals(ApiErrorMessageEnum.BCV_BOOK_NOT_FOUND.name(), exception.getMessage());
	}

	@Test
	public void testUpdateBookById_BookExists() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		when(bookRepository.save(any(Book.class))).thenReturn(book);
		when(bookMapper.mapBookReqModelToBook(any(BookReqModel.class), eq(book))).thenReturn(book);
		when(bookMapper.mapToBookResModel(book)).thenReturn(bookResModel);

		BookResModel result = bookService.updateBookById(1, bookReqModel);
		assertNotNull(result);
		assertEquals("Test Title", result.getTitle());
	}

	@Test
	public void testUpdateBookById_BookNotExists() {
		when(bookRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> bookService.updateBookById(1, bookReqModel));
		assertEquals(ApiErrorMessageEnum.BCV_BOOK_NOT_FOUND.name(), exception.getMessage());
	}

	@Test
	public void testDeleteBookById_BookExists() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		doNothing().when(borrowingRecordRepository).deleteAllByBookId(1);
		doNothing().when(bookRepository).deleteById(1);

		assertDoesNotThrow(() -> bookService.deleteBookById(1));
		verify(bookRepository, times(1)).deleteById(1);
	}

	@Test
	public void testDeleteBookById_BookNotExists() {
		when(bookRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> bookService.deleteBookById(1));
		assertEquals(ApiErrorMessageEnum.BCV_BOOK_NOT_FOUND.name(), exception.getMessage());
	}
}