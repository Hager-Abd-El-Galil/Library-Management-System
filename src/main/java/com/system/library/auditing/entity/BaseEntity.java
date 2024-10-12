package com.system.library.auditing.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.system.library.security.entity.UserCredentials;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1730760399636251291L;

	@CreatedDate
	@Column(name = "CREATE_DATE", nullable = false, updatable = false)
	private LocalDateTime createDate;

	@CreatedBy
	@OneToOne
	@JoinColumn(name = "CREATOR_USER_ID", nullable = false, updatable = false)
	private UserCredentials creator;

	@LastModifiedDate
	@Column(name = "CHANGE_DATE", nullable = true)
	private LocalDateTime changeDate;

	@LastModifiedBy
	@OneToOne
	@JoinColumn(name = "CHANGER_USER_ID", nullable = true)
	private UserCredentials changer;

}
