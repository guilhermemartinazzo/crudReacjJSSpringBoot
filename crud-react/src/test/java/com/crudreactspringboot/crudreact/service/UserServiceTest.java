package com.crudreactspringboot.crudreact.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.crudreactspringboot.crudreact.entity.User;
import com.crudreactspringboot.crudreact.exception.BusinessException;
import com.crudreactspringboot.crudreact.repository.UserRepository;
import com.crudreactspringboot.crudreact.vo.UserVO;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

	@SpyBean
	private UserService service;

	@MockBean
	private UserRepository userRepository;

	@Test
	void createUser() {
		Mockito.doNothing().when(service).validateEmail(anyString());
		String name = "name";
		String email = "email";
		String pw = "password";
		User userExpected = createUser(name, email, pw);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(userExpected);
		User userCreated = service.createUser(createUserVO(name, email, pw));
		assertThat(userExpected).isEqualTo(userCreated);
	}

	@Test
	void createUserBusinessException() {
		Mockito.doThrow(BusinessException.class).when(service).validateEmail(anyString());
		try {
			service.createUser(new UserVO());
		} catch (BusinessException e) {
			assertThat(e).isNotNull();
			verify(userRepository, never()).save(createUser(null, null, null));
		}
	}

	private UserVO createUserVO(String name, String email, String pw) {
		UserVO userVO = new UserVO();
		userVO.setEmail(email);
		userVO.setName(name);
		userVO.setPassword(pw);
		return userVO;
	}

	@Test
	void authenticate() {
		User user = createUser("name", "email", "password");
		Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		User userAuthenticated = service.authenticate(user.getEmail(), user.getPassword());
		assertEquals(user, userAuthenticated);
	}

	@Test
	void authenticateException_PasswordIncorrect() {
		User user = createUser("name", "email", "password");
		Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		try {
			service.authenticate(user.getEmail(), user.getPassword() + "lala");
		} catch (BusinessException e) {
			assertEquals("User not authenticated! Verify email/password", e.getMessage());
		}
	}

	@Test
	void authenticateException_EmailNotFound() {
		User user = createUser("name", "email", "password");
		Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		try {
			service.authenticate(user.getEmail(), user.getPassword());
		} catch (BusinessException e) {
			assertEquals("User not authenticated! Verify email/password", e.getMessage());
		}
	}

	@Test
	void validateEmail() {
		String email = "anyemailTest@testing.com";
		Mockito.when(userRepository.existsByEmail(email)).thenReturn(false);
		service.validateEmail(email);
	}

	@Test
	void validateEmailBusinessException() {
		String email = "emailTest@testing.com";
		Mockito.when(userRepository.existsByEmail(email)).thenReturn(true);
		try {
			service.validateEmail(email);
		} catch (BusinessException e) {
			Assertions.assertEquals("Please user other email to create a user!", e.getMessage());
		}
	}

	private User createUser(String name, String email, String pw) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(pw);
		return user;
	}

}
