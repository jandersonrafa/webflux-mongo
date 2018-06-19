package com.article.article.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.article.article.model.Event;

import reactor.core.publisher.Flux;

/**
 *
 * @author Janderson
 */
@Repository
public interface EventRepository extends ReactiveMongoRepository<Event, String> {

	public Flux<Event> findByUser_Id(String userId);
}
