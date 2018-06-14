package com.article.article.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import com.article.article.model.User;

/**
 *
 * @author Janderson
 */
@Ignore
public class UserRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	ReactiveMongoOperations operations;

	User user = new User();

	@Before
	public void setUp() {
		user.setEmail("asdsa");
		user.setName("asdsa");
		user.setPassword("asdsa");
		user.setUsername("asdsa");
		userRepository.save(user).then().block();
	}

	@Test
	public void findByNameAndImageUrlWithStringQueryTest() {

		final User re = userRepository.findById(user.getId()).block();
		assertThat(user).isNotNull();
	}

}
