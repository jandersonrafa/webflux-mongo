package com.article.article.service.event;

import com.article.article.dto.EventDetailDto;
import com.article.article.dto.output.EventListingDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Janderson
 */
public interface EventService {

	public Flux<EventListingDto> findAll();

	public Mono<EventDetailDto> findById(String eventId);
}
