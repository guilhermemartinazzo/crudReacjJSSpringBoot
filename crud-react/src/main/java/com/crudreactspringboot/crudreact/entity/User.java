package com.crudreactspringboot.crudreact.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um usuário
 * 
 * @author Guilherme
 *
 */
@Data
@Entity
@Table(name="user_table", schema = "finances")
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate dateRegistration;

	public User(Long id) {
		this.id = id;
	}
}
