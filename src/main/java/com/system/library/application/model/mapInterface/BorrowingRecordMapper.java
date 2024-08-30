package com.system.library.application.model.mapInterface;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.system.library.application.entity.Book;
import com.system.library.application.entity.BorrowingRecord;
import com.system.library.application.entity.Patron;
import com.system.library.application.model.response.BorrowingRecordResModel;

@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper {

	BorrowingRecordResModel mapToBorrowingRecordResModel(BorrowingRecord borrowingRecord);

	@Mapping(target = "id", ignore = true)
	BorrowingRecord mapToBorrowingRecord(Book book, Patron patron, LocalDate borrowingDate);

}
