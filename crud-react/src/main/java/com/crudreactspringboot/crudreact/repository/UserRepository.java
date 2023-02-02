package com.crudreactspringboot.crudreact.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crudreactspringboot.crudreact.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByEmail(String email);
	public Optional<User> findByName(String name);
	public boolean existsByEmail(String email);
}
