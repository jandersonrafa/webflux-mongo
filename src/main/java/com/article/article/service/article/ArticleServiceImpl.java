package com.article.article.service.article;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.article.article.dto.SubmissionDetailDto;
import com.article.article.dto.output.SubmissionListingDto;
import com.article.article.exception.NotFoundException;
import com.article.article.model.Article;
import com.article.article.model.Event;
import com.article.article.repository.ArticleRepository;
import com.article.article.repository.ArticleSimpleRepository;
import com.article.article.service.event.EventService;
import com.article.article.service.user.UserService;
import com.article.article.service.validator.ValidatorService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Janderson
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleSimpleRepository articleSimpleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private UserService userService;

	@Autowired
	private EventService eventService;

	@Override
	public Flux<SubmissionListingDto> findEventWithArticleLoggedUser() {
		final String loggedUserId = userService.getLoggedUser().getId();
//		final List<Article> articles = articleRepository.findAll().collect(Collectors.toList()).block();
		return articleRepository.findByUser_id(loggedUserId).sort().map(article -> {
			Event event = article.getEvent();
			final SubmissionListingDto submission = modelMapper.map(event, SubmissionListingDto.class);
			submission.setArticleId(article.getArticleId());
			return submission;
		});
	}
//	@Override
//	public Flux<SubmissionListingDto> findEventWithArticleLoggedUser() {
//		final String loggedUserId = userService.getLoggedUser().getId();
////		final List<Article> articles = articleRepository.findAll().collect(Collectors.toList()).block();
//		final List<Article> articles = articleRepository.findByUser_id(loggedUserId).sort().collect(Collectors.toList()).block();
//		//		final List<Article> articles = articleRepository.findByUser_id(loggedUserId);
//		final Set<Event> eventsWithSumissionLoggedUser = articles.stream().map(Article::getEvent).filter(Objects::nonNull).collect(toSet());
//		// TODO filtrar por eventos do usuario
//		return Flux.just(eventsWithSumissionLoggedUser.toArray(new Event[eventsWithSumissionLoggedUser.size()])).map(entity -> {
//			return modelMapper.map(entity, SubmissionListingDto.class);
//		});
//	}

	@Override
	public Mono<SubmissionDetailDto> findById(String articleId) {
		return articleRepository.findById(articleId).map(entity -> {
			final SubmissionDetailDto dto = modelMapper.map(entity, SubmissionDetailDto.class);
			dto.setEvent(entity.getEvent());
			return dto;
		});
	}

	@Override
	public List<Article> findByEventId(String eventId) {
		return articleSimpleRepository.findByEvent_EventId(eventId);
	}

	@Override
	public void delete(String submissionId) {
		final Optional<Article> optional = articleRepository.findById(submissionId).blockOptional();
		final String loggedUserId = userService.getLoggedUser().getId();
		if (optional.isPresent() || !optional.get().getUser().getId().equals(loggedUserId)) {
			articleRepository.delete(optional.get()).block();
		} else {
			throw new NotFoundException("Artigo não encontrado");
		}
	}

	@Override
	public Mono<SubmissionDetailDto> update(String articleId, SubmissionDetailDto dto) {
		validatorService.validate(dto);
		final Optional<Article> optional = articleRepository.findById(articleId).blockOptional();
		final String loggedUserId = userService.getLoggedUser().getId();
		if (optional.isPresent() || !optional.get().getUser().getId().equals(loggedUserId)) {
			final Article model = optional.get();
			model.setFileName("teste");
			model.setResume(dto.getResume());
			model.setSubmissionDate(LocalDate.now());
			model.setTitle(dto.getTitle());
			model.setUser(userService.getLoggedUser());
			return articleRepository.save(model).map(entity -> modelMapper.map(entity, SubmissionDetailDto.class));
		} else {
			throw new NotFoundException("Artigo não encontrado");
		}
	}

	@Override
	public Mono<SubmissionDetailDto> create(SubmissionDetailDto dto, String idEvento) {
		validatorService.validate(dto);
		final Article model = modelMapper.map(dto, Article.class);
		model.setUser(userService.getLoggedUser());
		model.setEvent(eventService.findModelById(idEvento));
		model.setSubmissionDate(LocalDate.now());
		model.setFileName("teste");
		return articleRepository.save(model).map(entity -> modelMapper.map(entity, SubmissionDetailDto.class));
	}
}
