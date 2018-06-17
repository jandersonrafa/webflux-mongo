package com.article.article.service.event;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.article.article.dto.EventDetailDto;
import com.article.article.dto.output.EventListingDto;
import com.article.article.repository.EventRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Janderson
 */
@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Flux<EventListingDto> findAll() {
		// TODO filtrar por eventos do usuario
		return eventRepository.findAll().map(entity -> {
			return modelMapper.map(entity, EventListingDto.class);
		});
	}

	@Override
	public Mono<EventDetailDto> findById(String eventId) {
		return eventRepository.findById(eventId).map(entity -> modelMapper.map(entity, EventDetailDto.class));
	}
}
