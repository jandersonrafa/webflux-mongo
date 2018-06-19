package com.article.article.service.Submission;

import com.article.article.dto.SubmissionDetailDto;
import com.article.article.dto.output.SubmissionListingDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Janderson
 */
public interface SubmissionService {

	public Flux<SubmissionListingDto> findEventUserSubmissionArticle();

}
