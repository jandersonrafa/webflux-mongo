package com.article.article.service.user;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.article.article.dto.UserDetailDto;
import com.article.article.dto.output.UserListingDto;
import com.article.article.exception.NotFoundException;
import com.article.article.exception.UnauthorizedException;
import com.article.article.exception.ValidateException;
import com.article.article.model.User;
import com.article.article.repository.UserRepository;
import com.article.article.service.validator.ValidatorService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Flux;

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

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
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

        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new ValidateException("Senha e confirmação de senha não são iguais.");
        }

//        TODO: colocar encode no password
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user).map(entity -> modelMapper.map(entity, UserDetailDto.class));
    }

    @Override
    public User getLoggedUser() {
        // TODO get id usuario logado
        return User.builder().id("28200889534180877342847324285").build();
    }

    @Override
    public Flux<UserListingDto> findAll() {
        return userRepository.findAll().map(entity -> {
            return modelMapper.map(entity, UserListingDto.class);
        });
    }

}
