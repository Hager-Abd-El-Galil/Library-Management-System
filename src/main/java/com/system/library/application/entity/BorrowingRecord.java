package com.system.library.application.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BorrowingRecord", 
	   uniqueConstraints = { 
			   @UniqueConstraint(columnNames = {"PATRON_ID", "BOOK_ID", "borrowingDate"})
					   })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8984449791391572712L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private LocalDate borrowingDate;
	
	private LocalDate returnDate;
	
	@ManyToOne
	@JoinColumn(name = "PATRON_ID", nullable = false, foreignKey = @ForeignKey(name = "BorrowingRecord_Patron"))
	private Patron patron;
	
	@ManyToOne
	@JoinColumn(name = "BOOK_ID", nullable = false, foreignKey = @ForeignKey(name = "BorrowingRecord_BOOK"))
	private Book book;
	
	

}
