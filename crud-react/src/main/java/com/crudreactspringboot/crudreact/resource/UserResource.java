package com.crudreactspringboot.crudreact.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudreactspringboot.crudreact.entity.User;
import com.crudreactspringboot.crudreact.exception.BusinessException;
import com.crudreactspringboot.crudreact.service.UserService;
import com.crudreactspringboot.crudreact.vo.UserVO;

@RestController
@RequestMapping("/user")
public class UserResource {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<User>> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserVO user) {
		try {
			User userCreated = service.createUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
		} catch (BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
