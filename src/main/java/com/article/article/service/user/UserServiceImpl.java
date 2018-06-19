package com.article.article.service.user;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.article.article.dto.UserDetailDto;
import com.article.article.exception.NotFoundException;
import com.article.article.exception.UnauthorizedException;
import com.article.article.model.User;
import com.article.article.repository.UserRepository;
import com.article.article.service.validator.ValidatorService;

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

	@Autowired
	private ValidatorService validatorService;

	@Override
	public Mono<UserDetailDto> findById(String userId) {
		final String loggedUserId = this.getLoggedUser().getId();
		if (loggedUserId.equals(userId)) {
			return userRepository.findById(loggedUserId).map(entity -> modelMapper.map(entity, UserDetailDto.class));
		} else {
			throw new UnauthorizedException("Usuário não permitido");
		}
	}

	@Override
	public Mono<UserDetailDto> update(String userId, @Valid UserDetailDto dto) {
		validatorService.validate(dto);
		final Optional<User> optUser = userRepository.findById(userId).blockOptional();
		if (optUser.isPresent()) {
			final User user = optUser.get();
			user.setEmail(dto.getEmail());
			user.setName(dto.getName());
			user.setUsername(dto.getUsername());
			return userRepository.save(user).map(entity -> modelMapper.map(entity, UserDetailDto.class));
		} else {
			throw new NotFoundException("Usuário não encontrado");
		}
	}

	@Override
	public User getLoggedUser() {
		// TODO get id usuario logado
		return User.builder().id("28200889534180877342847324285").build();
	}

}
