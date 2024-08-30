package com.system.library.application.repository;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.system.library.application.entity.Patron;
import com.system.library.application.filter.PatronFilter;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Integer> {

	@Query("SELECT patrons FROM Patron patrons "
			+ "WHERE (((:#{#patronFilter.name} IS NULL) OR (patrons.name LIKE %:#{#patronFilter.name}%)) "
			+ "AND ((:#{#patronFilter.email} IS NULL) OR (patrons.contactInfo.email LIKE %:#{#patronFilter.email}%)) "
			+ "AND ((:#{#patronFilter.phone} IS NULL) OR (patrons.contactInfo.phone LIKE %:#{#patronFilter.phone}%)))")
	Page<Patron> findAllFilteredPatrons(PatronFilter patronFilter, Pageable pageableRequest);

	Patron findByContactInfoPhone(@NotNull String phone);

	Patron findByContactInfoEmail(@NotNull String email);

}
