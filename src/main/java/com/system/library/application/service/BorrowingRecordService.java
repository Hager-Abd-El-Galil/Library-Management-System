package com.system.library.application.service;

import com.system.library.application.model.response.BorrowingRecordResModel;

public interface BorrowingRecordService {

	BorrowingRecordResModel borrowBook(int bookId, int patronId);

	BorrowingRecordResModel returnBook(int bookId, int patronId);

}
