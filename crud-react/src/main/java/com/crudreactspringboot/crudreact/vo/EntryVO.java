package com.crudreactspringboot.crudreact.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.crudreactspringboot.crudreact.enumeration.EntryStatus;
import com.crudreactspringboot.crudreact.enumeration.EntryType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryVO {
	private Long id;
	private String description;
	private Integer entryYear;
	private Integer entryMonth;
	private BigDecimal entryValue;
	private Long user;
	private EntryStatus status;
	private EntryType type;
	private LocalDate dateRegistration;
}
