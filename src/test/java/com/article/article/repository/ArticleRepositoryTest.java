package com.article.article.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import com.article.article.model.Article;
import com.article.article.model.User;

/**
 *
 * @author Janderson
 */
public class ArticleRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	ReactiveMongoOperations operations;

	Article article = new Article();

	@Before
	public void setUp() {
		User user = new User();
		user.setEmail("asdsa");
		user.setName("asdsa");
		user.setPassword("asdsa");
		user.setUsername("asdsa");
		userRepository.save(user).then().block();

		article.setFileName("asdsa");
		article.setResume("asdsa");
		article.setSubmissionDate(LocalDate.now());
		article.setTitle("asdsa");
		article.setUser(user);
		articleRepository.save(article).then().block();
	}

	@Test
	public void findByNameAndImageUrlWithStringQueryTest() {

		final Article re = articleRepository.findById(article.getArticleId()).block();
		assertThat(article).isNotNull();
	}

}
