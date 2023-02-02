package com.crudreactspringboot.crudreact.vo;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {

	private String name;
	private String email;
	private String password;
	private LocalDate dateRegistration;
}
