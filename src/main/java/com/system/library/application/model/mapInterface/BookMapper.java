package com.system.library.application.model.mapInterface;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.system.library.application.entity.Book;
import com.system.library.application.model.request.BookReqModel;
import com.system.library.application.model.response.BookResModel;

@Mapper(componentModel = "spring")
public interface BookMapper {

	List<BookResModel> mapToBookResModel(List<Book> book);

	BookResModel mapToBookResModel(Book book);

	Book mapToBook(BookReqModel bookReqModel);

	Book mapBookReqModelToBook(BookReqModel bookReqModel, @MappingTarget Book book);

}
