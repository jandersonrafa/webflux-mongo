package com.article.article.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.article.article.model.Event;

/**
 *
 * @author Janderson
 */
@Repository
public interface EventRepository extends ReactiveMongoRepository<Event, String> {

}
