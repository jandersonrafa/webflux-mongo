package com.article.article.service.event;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.article.article.dto.EventArticleDetailDto;
import com.article.article.dto.EventDetailDto;
import com.article.article.dto.output.EventListingDto;
import com.article.article.exception.BadRequestException;
import com.article.article.exception.NotFoundException;
import com.article.article.model.Article;
import com.article.article.model.Event;
import com.article.article.repository.EventRepository;
import com.article.article.service.article.ArticleService;
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

	@Autowired
	private ArticleService articleService;

	@Override
	public Flux<EventListingDto> findAll() {
		// TODO filtrar por eventos do usuario
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return eventRepository.findByUser_Id(userService.getLoggedUser().getId()).map(entity -> {

			final EventListingDto dto = modelMapper.map(entity, EventListingDto.class);
			dto.setDsEventDate(entity.getEventDate().format(formatter));
			dto.setDsRegistrationEndDate(entity.getRegistrationEndDate().format(formatter));
			dto.setDsRegistrationStartDate(entity.getRegistrationStartDate().format(formatter));
			dto.setUserCreated(entity.getUser().getName());
			return dto;
		});
	}

	@Override
	public Mono<EventDetailDto> findById(String eventId) {
		return eventRepository.findById(eventId).map(entity -> {
			final EventDetailDto dto = modelMapper.map(entity, EventDetailDto.class);
			final List<Article> articles = articleService.findByEventId(eventId);
			dto.setArticles(articles.stream().map(article -> modelMapper.map(article, EventArticleDetailDto.class)).collect(toList()));
			return dto;
		});
	}

	@Override
	public void delete(String eventId) {
		final Optional<Event> optional = eventRepository.findById(eventId).blockOptional();
		final String loggedUserId = userService.getLoggedUser().getId();
		if (optional.isPresent() || !optional.get().getUser().getId().equals(loggedUserId)) {
			eventRepository.delete(optional.get()).block();
		} else {
			throw new NotFoundException("Evento não encontrado");
		}
	}

	@Override
	public Mono<EventDetailDto> update(String eventId, EventDetailDto dto) {
		validatorService.validate(dto);
		validate(dto);
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
		validate(dto);
		final Event model = modelMapper.map(dto, Event.class);
		model.setUser(userService.getLoggedUser());
		return eventRepository.save(model).map(entity -> modelMapper.map(entity, EventDetailDto.class));
	}

	@Override
	public Event findModelById(String idEvento) {
		return eventRepository.findById(idEvento).block();
	}

	private void validate(EventDetailDto dto) {
		if (dto.getEventDate().isBefore(LocalDate.now())) {
			throw new BadRequestException("Data evento não pode ser menor que a data atual");
		}
		if (dto.getRegistrationEndDate().isBefore(dto.getRegistrationStartDate())) {
			throw new BadRequestException("Data encerramento inscrição não pode ser menor que data de abertura");
		}
	}
}
