package com.crudreactspringboot.crudreact.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.crudreactspringboot.crudreact.enumeration.EntryStatus;
import com.crudreactspringboot.crudreact.enumeration.EntryType;

import lombok.Data;

/**
 * Representa um lancamento (finanças)
 * 
 * @author Guilherme
 *
 */
@Data
@Entity
@Table(name = "entry", schema = "finances")
public class Entry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	private Integer entryYear;

	private Integer entryMonth;

	private BigDecimal entryValue;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "id_user")
	private User user;

	@Enumerated(EnumType.STRING)
	private EntryStatus status;

	@Enumerated(EnumType.STRING)
	private EntryType type;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate dateRegistration;
}
