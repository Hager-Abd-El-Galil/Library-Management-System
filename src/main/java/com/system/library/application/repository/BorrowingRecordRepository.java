package com.system.library.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.library.application.entity.BorrowingRecord;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {

	void deleteAllByBookId(int bookId);
	
	void deleteAllByPatronId(int patronId);

}
