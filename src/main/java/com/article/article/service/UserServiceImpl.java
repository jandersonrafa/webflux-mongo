package com.article.article.service;

import java.math.BigInteger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.article.article.dto.output.UserDetailDto;
import com.article.article.repository.UserRepository;

import reactor.core.publisher.Mono;

/**
 *
 * @author Janderson
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Mono<UserDetailDto> findById(BigInteger userId) {
		return userRepository.findById(userId).map(entity -> {
			final UserDetailDto dto = modelMapper.map(entity, UserDetailDto.class);
			return dto;
		});
	}

}
