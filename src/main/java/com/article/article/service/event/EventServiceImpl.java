package com.article.article.service.event;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.article.article.dto.EventDetailDto;
import com.article.article.dto.output.EventListingDto;
import com.article.article.exception.NotFoundException;
import com.article.article.model.Event;
import com.article.article.repository.EventRepository;
import com.article.article.service.user.UserService;
import com.article.article.service.validator.ValidatorService;

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

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private UserService userService;

	@Override
	public Flux<EventListingDto> findAll() {
		// TODO filtrar por eventos do usuario
		return eventRepository.findByUser_Id(userService.getLoggedUser().getId()).map(entity -> {
			return modelMapper.map(entity, EventListingDto.class);
		});
	}

	@Override
	public Mono<EventDetailDto> findById(String eventId) {
		return eventRepository.findById(eventId).map(entity -> modelMapper.map(entity, EventDetailDto.class));
	}

	@Override
	public Mono<EventDetailDto> update(String eventId, EventDetailDto dto) {
		validatorService.validate(dto);
		final Optional<Event> optional = eventRepository.findById(eventId).blockOptional();
		final String loggedUserId = userService.getLoggedUser().getId();
		if (optional.isPresent() || !optional.get().getUser().getId().equals(loggedUserId)) {
			final Event model = optional.get();
			model.setEventDate(dto.getEventDate());
			model.setEventName(dto.getEventName());
			model.setRegistrationStartDate(dto.getRegistrationStartDate());
			model.setRegistrationEndDate(dto.getRegistrationEndDate());
			model.setUser(userService.getLoggedUser());
			return eventRepository.save(model).map(entity -> modelMapper.map(entity, EventDetailDto.class));
		} else {
			throw new NotFoundException("Evento não encontrado");
		}
	}

	@Override
	public Mono<EventDetailDto> create(EventDetailDto dto) {
		validatorService.validate(dto);
		final Event model = modelMapper.map(dto, Event.class);
		model.setUser(userService.getLoggedUser());
		return eventRepository.save(model).map(entity -> modelMapper.map(entity, EventDetailDto.class));
	}
}
