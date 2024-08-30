package com.system.library.application.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.system.library.application.entity.BorrowingRecord;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {

	void deleteAllByBookId(int bookId);

	void deleteAllByPatronId(int patronId);

	@Query("SELECT b FROM BorrowingRecord b " + "WHERE b.book.id = :bookId " + "AND b.borrowingDate <= :borrowingDate "
			+ "AND (b.returnDate IS NULL OR b.returnDate > :borrowingDate)")
	BorrowingRecord findBorrowedBookById(int bookId, LocalDate borrowingDate);

	BorrowingRecord findByBookIdAndPatronIdAndReturnDateIsNull(int bookId, int patronId);

}
