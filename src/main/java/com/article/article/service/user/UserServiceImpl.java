package com.article.article.service.user;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.article.article.dto.UserDetailDto;
import com.article.article.dto.output.UserListingDto;
import com.article.article.exception.NotFoundException;
import com.article.article.exception.UnauthorizedException;
import com.article.article.exception.ValidateException;
import com.article.article.model.LoggedUser;
import com.article.article.model.User;
import com.article.article.repository.UserRepository;
import com.article.article.service.validator.ValidatorService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Janderson
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ValidatorService validatorService;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	public Mono<UserDetailDto> findById(String userId) {
		return userRepository.findById(userId).map(entity -> modelMapper.map(entity, UserDetailDto.class));
	}

	@Override
	public Mono<UserDetailDto> update(String userId, @Valid UserDetailDto dto) {
		validatorService.validate(dto);
		final Optional<User> optUser = userRepository.findById(userId).blockOptional();
		if (optUser.isPresent()) {
			final User user = optUser.get();
			if (user.getId().equals(getLoggedUser().getId())) {

				user.setEmail(dto.getEmail());
				user.setName(dto.getName());
				user.setUsername(dto.getUsername());
				user.setPassword(dto.getPassword());
				return userRepository.save(user).map(entity -> modelMapper.map(entity, UserDetailDto.class));
			} else {
				throw new UnauthorizedException("Usuário não permitido");
			}
		} else {
			throw new NotFoundException("Usuário não encontrado");
		}
	}

	@Override
	public Mono<UserDetailDto> save(@Valid UserDetailDto dto, RedirectAttributes redirectAttrs) {
		validatorService.validate(dto);
		User user = modelMapper.map(dto, User.class);

		Optional<User> findByUsername = userRepository.findByUsername(dto.getUsername()).blockOptional();

		if (findByUsername.isPresent()) {
			throw new ValidateException("Um usuário com este nome de usuário já está cadastrado.");
		}

		if (dto.getPassword().length() == 0) {
			throw new ValidateException("Uma senha deve ser informada.");
		}

		return userRepository.save(user).map(entity -> modelMapper.map(entity, UserDetailDto.class));
	}

	@Override
	public User getLoggedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ((LoggedUser) auth.getPrincipal()).getUser();
	}

	@Override
	public Flux<UserListingDto> findAll() {
		return userRepository.findAll().map(entity -> {
			return modelMapper.map(entity, UserListingDto.class);
		});
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username).block();

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//		for (Role role : user.getRoles()) {
//			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//		}
		grantedAuthorities.add(new SimpleGrantedAuthority("PUBLIC"));
		return new LoggedUser(user);
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}

}
