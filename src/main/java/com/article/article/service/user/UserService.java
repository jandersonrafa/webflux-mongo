package com.article.article.service.user;

import javax.validation.Valid;

import com.article.article.dto.UserDetailDto;
import com.article.article.dto.output.UserListingDto;
import com.article.article.model.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Flux;

import reactor.core.publisher.Mono;

/**
 *
 * @author Janderson
 */
public interface UserService {

    public Mono<UserDetailDto> findById(String userId);

    public Mono<UserDetailDto> update(String userId, @Valid UserDetailDto dto);

    public Mono<UserDetailDto> save(@Valid UserDetailDto dto, RedirectAttributes redirectAttrs);

    public User getLoggedUser();

    public Flux<UserListingDto> findAll();

}
