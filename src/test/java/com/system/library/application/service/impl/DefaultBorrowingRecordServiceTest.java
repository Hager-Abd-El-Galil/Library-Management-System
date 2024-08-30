package com.system.library.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.system.library.application.entity.Book;
import com.system.library.application.entity.BorrowingRecord;
import com.system.library.application.entity.Patron;
import com.system.library.application.model.mapInterface.BorrowingRecordMapper;
import com.system.library.application.model.response.BookResModel;
import com.system.library.application.model.response.BorrowingRecordResModel;
import com.system.library.application.model.response.PatronResModel;
import com.system.library.application.repository.BookRepository;
import com.system.library.application.repository.BorrowingRecordRepository;
import com.system.library.application.repository.PatronRepository;
import com.system.library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.library.utils.exception.impl.BusinessLogicViolationException;

@ExtendWith(MockitoExtension.class)
public class DefaultBorrowingRecordServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private PatronRepository patronRepository;

	@Mock
	private BorrowingRecordRepository borrowingRecordRepository;

	@Mock
	private BorrowingRecordMapper borrowingRecordMapper;

	@InjectMocks
	private DefaultBorrowingRecordService borrowingRecordService;

	private Book book;
	private Patron patron;
	private BorrowingRecord borrowingRecord;
	private BookResModel bookResModel;
	private PatronResModel patronResModel;
	private BorrowingRecordResModel borrowingRecordResModel;

	@BeforeEach
	public void setup() {

		book = new Book();
		book.setId(1);
		book.setTitle("Effective Java");

		patron = new Patron();
		patron.setId(1);
		patron.setName("John Doe");

		borrowingRecord = new BorrowingRecord();
		borrowingRecord.setId(1);
		borrowingRecord.setBook(book);
		borrowingRecord.setPatron(patron);
		borrowingRecord.setBorrowingDate(LocalDate.now());

		bookResModel = new BookResModel();
		bookResModel.setTitle("Effective Java");

		patronResModel = new PatronResModel();
		patronResModel.setName("John Doe");

		borrowingRecordResModel = new BorrowingRecordResModel();
		borrowingRecordResModel.setBook(bookResModel);
		borrowingRecordResModel.setPatron(patronResModel);
		borrowingRecordResModel.setBorrowingDate(LocalDate.now());
		borrowingRecordResModel.setReturnDate(null);

	}

	@Test
	public void testBorrowBook_Success() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
		when(borrowingRecordRepository.findBorrowedBookById(1, LocalDate.now())).thenReturn(null);
		when(borrowingRecordMapper.mapToBorrowingRecord(book, patron, LocalDate.now())).thenReturn(borrowingRecord);
		when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
		when(borrowingRecordMapper.mapToBorrowingRecordResModel(borrowingRecord)).thenReturn(borrowingRecordResModel);

		BorrowingRecordResModel result = borrowingRecordService.borrowBook(1, 1);
		assertNotNull(result);
		assertEquals("Effective Java", result.getBook().getTitle());
		assertEquals("John Doe", result.getPatron().getName());
	}

	@Test
	public void testBorrowBook_BookNotFound() {
		when(bookRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> borrowingRecordService.borrowBook(1, 1));
		assertEquals(ApiErrorMessageEnum.BCV_BOOK_NOT_FOUND.name(), exception.getMessage());
	}

	@Test
	public void testBorrowBook_PatronNotFound() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		when(patronRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> borrowingRecordService.borrowBook(1, 1));
		assertEquals(ApiErrorMessageEnum.BCV_PATRON_NOT_FOUND.name(), exception.getMessage());
	}

	@Test
	public void testBorrowBook_BookAlreadyBorrowed() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
		when(borrowingRecordRepository.findBorrowedBookById(1, LocalDate.now())).thenReturn(borrowingRecord);

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> borrowingRecordService.borrowBook(1, 1));
		assertEquals(ApiErrorMessageEnum.BCV_BOOK_IS_ALREADY_BORROWED.name(), exception.getMessage());
	}

	@Test
	public void testReturnBook_Success() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
		when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1, 1)).thenReturn(borrowingRecord);
		when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
		when(borrowingRecordMapper.mapToBorrowingRecordResModel(borrowingRecord)).thenReturn(borrowingRecordResModel);

		BorrowingRecordResModel result = borrowingRecordService.returnBook(1, 1);
		assertNotNull(result);
		assertEquals("Effective Java", result.getBook().getTitle());
		assertEquals("John Doe", result.getPatron().getName());
		assertNotNull(result.getReturnDate());
	}

	@Test
	public void testReturnBook_BookNotFound() {
		when(bookRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> borrowingRecordService.returnBook(1, 1));
		assertEquals(ApiErrorMessageEnum.BCV_BOOK_NOT_FOUND.name(), exception.getMessage());
	}

	@Test
	public void testReturnBook_PatronNotFound() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		when(patronRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> borrowingRecordService.returnBook(1, 1));
		assertEquals(ApiErrorMessageEnum.BCV_PATRON_NOT_FOUND.name(), exception.getMessage());
	}

	@Test
	public void testReturnBook_NotBorrowed() {
		when(bookRepository.findById(1)).thenReturn(Optional.of(book));
		when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
		when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1, 1)).thenReturn(null);

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class,
				() -> borrowingRecordService.returnBook(1, 1));
		assertEquals(ApiErrorMessageEnum.BCV_BOOK_IS_NOT_BORROWED_WITH_PATRON.name(), exception.getMessage());
	}
}
