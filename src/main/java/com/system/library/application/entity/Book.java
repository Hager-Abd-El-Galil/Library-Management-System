package com.system.library.application.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.system.library.auditing.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Book", uniqueConstraints = { @UniqueConstraint(columnNames = { "title", "author" }) })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5458992834404998878L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 150)
	private String title;

	@Column(nullable = false, length = 150)
	private String author;

	@Column(nullable = false)
	private int publicationYear;

	@Column(nullable = false, length = 13, unique = true)
	private String isbn;

	@OneToMany(mappedBy = "book")
	private List<BorrowingRecord> borrowingRecords;
}
