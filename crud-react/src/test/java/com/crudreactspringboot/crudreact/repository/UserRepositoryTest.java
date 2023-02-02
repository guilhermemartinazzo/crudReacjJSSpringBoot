package com.crudreactspringboot.crudreact.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.crudreactspringboot.crudreact.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UserRepositoryTest {

	@Autowired
	UserRepository repository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	void testSaveUser() {
		User user = createUser("userTest@test.com", "UserNameTest", "password");
		repository.save(user);
		assertThat(user.getId()).isNotNull();
	}

	@Test
	void testExistsByEmail() {
		User user = createUser("userTest@test.com", "UserNameTest", "password");
		entityManager.persist(user);
		boolean existsByEmail = repository.existsByEmail(user.getEmail());
		assertThat(existsByEmail).isTrue();
	}

	@Test
	void testExistsByEmail_False() {
		boolean existsByEmail = repository.existsByEmail("emailNotInDataBase@test.com");
		assertThat(existsByEmail).isFalse();
	}

	@Test
	void testfindByName() {
		User user = createUser("userTest@test.com", "UserNameTest", "password");
		entityManager.persist(user);
		Optional<User> userOpt = repository.findByName(user.getName());
		assertThat(userOpt).isPresent();
	}

	@Test
	void testfindByName_False() {
		Optional<User> userOpt = repository.findByName("name not in database");
		assertThat(userOpt).isNotPresent();
	}
	
	@Test
	void testfindByEmail() {
		User user = createUser("userTest@test.com", "UserNameTest", "password");
		entityManager.persist(user);
		Optional<User> userOpt = repository.findByEmail(user.getEmail());
		assertThat(userOpt).isPresent();
	}
	
	@Test
	void testfindByEmail_False() {
		Optional<User> userOpt = repository.findByEmail("emailnotindatabase@email.com");
		assertThat(userOpt).isNotPresent();
	}

	private User createUser(String email, String name, String password) {
		User user = new User();
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		return user;
	}
}
