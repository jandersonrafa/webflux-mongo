package com.article.article.service;

import java.math.BigInteger;

import com.article.article.dto.output.UserDetailDto;

import reactor.core.publisher.Mono;

/**
 *
 * @author Janderson
 */
public interface UserService {

	public Mono<UserDetailDto> findById(BigInteger userId);

}
