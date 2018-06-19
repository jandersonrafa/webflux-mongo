package com.article.article.service.user;

import javax.validation.Valid;

import com.article.article.dto.UserDetailDto;
import com.article.article.model.User;

import reactor.core.publisher.Mono;

/**
 *
 * @author Janderson
 */
public interface UserService {

	public Mono<UserDetailDto> findById(String userId);

	public Mono<UserDetailDto> update(String userId, @Valid UserDetailDto dto);

	public User getLoggedUser();

}
