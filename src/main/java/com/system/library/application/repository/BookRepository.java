package com.system.library.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.system.library.application.entity.Book;
import com.system.library.application.filter.BookFilter;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	@Query("SELECT books FROM Book books "
			+ "WHERE (((:#{#bookFilter.author} IS NULL) OR (books.author LIKE %:#{#bookFilter.author}%)) "
			+ "AND ((:#{#bookFilter.title} IS NULL) OR (books.title LIKE %:#{#bookFilter.title}%)) "
			+ "AND ((:#{#bookFilter.isbn} IS NULL) OR (books.isbn LIKE %:#{#bookFilter.isbn}%)) "
			+ "AND ((:#{#bookFilter.publicationYear} IS NULL) OR (books.publicationYear = :#{#bookFilter.publicationYear})))")
	Page<Book> findAllFilteredBooks(BookFilter bookFilter, Pageable pageableRequest);

	Book findByTitleAndAuthor(String title, String author);

	Book findByIsbn(String isbn);

}
