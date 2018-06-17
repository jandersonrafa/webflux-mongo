package com.article.article.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import com.article.article.model.Event;
import com.article.article.model.User;

/**
 *
 * @author Janderson
 */
@Ignore
public class EventRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	ReactiveMongoOperations operations;

	Event event = new Event();

	@Before
	public void setUp() {
		User user = new User();
		user.setEmail("asdsa");
		user.setName("asdsa");
		user.setPassword("asdsa");
		user.setUsername("asdsa");
		userRepository.save(user).then().block();

		event.setEventDate(LocalDate.now());
		event.setEventName("asdsa");
		event.setRegistrationEndDate(LocalDate.now());
		event.setRegistrationStartDate(LocalDate.now());
		event.setUser(user);
		eventRepository.save(event).then().block();
	}

	@Test
	public void findByNameAndImageUrlWithStringQueryTest() {

		final Event re = eventRepository.findById(event.getEventId()).block();
		assertThat(event).isNotNull();
	}

}
