package com.system.library.application.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.library.application.entity.Book;
import com.system.library.application.entity.BorrowingRecord;
import com.system.library.application.entity.Patron;
import com.system.library.application.model.mapInterface.BorrowingRecordMapper;
import com.system.library.application.model.response.BorrowingRecordResModel;
import com.system.library.application.repository.BookRepository;
import com.system.library.application.repository.BorrowingRecordRepository;
import com.system.library.application.repository.PatronRepository;
import com.system.library.application.service.BorrowingRecordService;
import com.system.library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.library.utils.exception.impl.BusinessLogicViolationException;

@Service
public class DefaultBorrowingRecordService implements BorrowingRecordService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	PatronRepository patronRepository;

	@Autowired
	BorrowingRecordRepository borrowingRecordRepository;

	@Autowired
	BorrowingRecordMapper borrowingRecordMapper;

	@Override
	public BorrowingRecordResModel borrowBook(int bookId, int patronId) {

		Book book = getBook(bookId);
		Patron patron = getPatron(patronId);
		validateBookNotBorrowed(bookId);

		BorrowingRecord BorrowingRecord = new BorrowingRecord();
		BorrowingRecord = borrowingRecordMapper.mapToBorrowingRecord(book, patron, LocalDate.now());
		borrowingRecordRepository.save(BorrowingRecord);

		return borrowingRecordMapper.mapToBorrowingRecordResModel(BorrowingRecord);
	}

	@Override
	public BorrowingRecordResModel returnBook(int bookId, int patronId) {

		Book book = getBook(bookId);
		Patron patron = getPatron(patronId);
		BorrowingRecord BorrowingRecord = getBorrowedRecord(bookId, patronId);
		BorrowingRecord.setReturnDate(LocalDate.now());
		borrowingRecordRepository.save(BorrowingRecord);

		return borrowingRecordMapper.mapToBorrowingRecordResModel(BorrowingRecord);
	}

	private Patron getPatron(int patronId) {
		Patron patron = patronRepository.findById(patronId).orElse(null);

		if (patron == null) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_NOT_FOUND.name());
		}

		return patron;
	}

	private Book getBook(int bookId) {
		Book book = bookRepository.findById(bookId).orElse(null);

		if (book == null) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_NOT_FOUND.name());
		}

		return book;
	}

	private void validateBookNotBorrowed(int bookId) {
		if (borrowingRecordRepository.findBorrowedBookById(bookId, LocalDate.now()) != null) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_IS_ALREADY_BORROWED.name());
		}
	}

	private BorrowingRecord getBorrowedRecord(int bookId, int patronId) {
		BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId,
				patronId);

		if (borrowingRecord == null) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_BOOK_IS_NOT_BORROWED_WITH_PATRON.name());
		}

		return borrowingRecord;
	}

}
