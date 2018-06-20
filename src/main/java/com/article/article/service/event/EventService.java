package com.article.article.service.event;

import com.article.article.dto.EventDetailDto;
import com.article.article.dto.output.EventListingDto;
import com.article.article.model.Event;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Janderson
 */
public interface EventService {

	public Flux<EventListingDto> findAll();

	public Mono<EventDetailDto> findById(String eventId);

	public Mono<EventDetailDto> update(String eventId, EventDetailDto dto);

	public Mono<EventDetailDto> create(EventDetailDto dto);

	public void delete(String eventId);

	public Event findModelById(String idEvento);

}
