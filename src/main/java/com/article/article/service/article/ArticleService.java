package com.article.article.service.article;

import java.util.List;

import com.article.article.dto.SubmissionDetailDto;
import com.article.article.dto.output.SubmissionListingDto;
import com.article.article.model.Article;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Janderson
 */
public interface ArticleService {

	public Flux<SubmissionListingDto> findEventWithArticleLoggedUser();

	public Mono<SubmissionDetailDto> findById(String articleId);

	public void delete(String submissionId);

	public Mono<SubmissionDetailDto> update(String submissionId, SubmissionDetailDto dto);

	public Mono<SubmissionDetailDto> create(SubmissionDetailDto dto, String idEvento);

	public List<Article> findByEventId(String eventId);

}
