package com.crudreactspringboot.crudreact.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crudreactspringboot.crudreact.entity.User;
import com.crudreactspringboot.crudreact.exception.BusinessException;
import com.crudreactspringboot.crudreact.repository.UserRepository;
import com.crudreactspringboot.crudreact.util.ObjectMapperUtils;
import com.crudreactspringboot.crudreact.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Transactional
	public User createUser(UserVO userVO) throws BusinessException {
		validateEmail(userVO.getEmail());
		User userEntity = ObjectMapperUtils.getInstance().convertValue(userVO, User.class);
		return repository.save(userEntity);
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public void deleteAll() {
		repository.deleteAll();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}

	public User authenticate(String email, String password) {
		Optional<User> userOpt = repository.findByEmail(email);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			if (user.getPassword().equals(password)) {
				return user;
			}
		}
		throw new BusinessException("User not authenticated! Verify email/password");
	}

	public void validateEmail(String email) throws BusinessException {
		if (repository.existsByEmail(email)) {
			throw new BusinessException("Please user other email to create a user!");
		}
	}
}
