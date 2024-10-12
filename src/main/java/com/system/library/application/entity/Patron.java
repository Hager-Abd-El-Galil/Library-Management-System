package com.system.library.application.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.system.library.auditing.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Patron")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patron extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6580037733771820801L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 150)
	private String name;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "email", column = @Column(nullable = false, length = 50)),
			@AttributeOverride(name = "phone", column = @Column(nullable = false, length = 11)),
			@AttributeOverride(name = "address.street", column = @Column(length = 50)),
			@AttributeOverride(name = "address.city", column = @Column(length = 50)),
			@AttributeOverride(name = "address.state", column = @Column(length = 50)),
			@AttributeOverride(name = "address.zipCode", column = @Column(length = 5)) })
	private ContactInfo contactInfo;

	@OneToMany(mappedBy = "patron")
	private List<BorrowingRecord> borrowingRecords;
}
