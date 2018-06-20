package com.article.article.controller;

import org.omg.CORBA.UserException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.article.article.dto.UserDetailDto;
import com.article.article.dto.output.UserListingDto;
import com.article.article.exception.AbstractBusinessException;
import com.article.article.service.user.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
@RequestMapping("/usuario")
public class UserController {

	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping
	@ApiOperation(value = "Página de Usuários")
	public ModelAndView pageUser() {
		return new ModelAndView("user/userList");
	}

	@ResponseBody
	@GetMapping(value = "/todos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ApiOperation(value = "Listagem de Usuários")
	public Flux<UserListingDto> listUser() {
		return userService.findAll();
	}

	@GetMapping("/cadastro/{userId}")
	@ApiOperation(value = "Exibe dados do usuário")
	public Mono<ModelAndView> exibeUsuario(@PathVariable("userId") String userId) {
		return userService.findById(userId).map(user -> new ModelAndView("user/userDetail", "user", user));
	}

	@GetMapping("/cadastro")
	@ApiOperation(value = "Exibe cadastro de usuário")
	public ModelAndView pageRegister() {
		return new ModelAndView("user/userDetail", "user", new UserDetailDto());
	}

	@PostMapping("{id}")
	@ApiOperation(value = "Alteração de dados de usuario")
	public String update(@PathVariable("id") String userId, UserDetailDto dto, RedirectAttributes redirectAttrs) throws UserException {
		try {
			// TODO redirecionar para evento quando sucesso
			redirectAttrs.addFlashAttribute("user", userService.update(userId, dto).block());
			return "redirect:/usuario/" + userId;
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", e instanceof AbstractBusinessException ? e.getMessage() : "Erro ao tentar salvar");
			redirectAttrs.addFlashAttribute("user", dto);
			return "redirect:/usuario/" + userId;
		}
	}

	@PostMapping
	@ApiOperation(value = "Alteração de dados de usuario")
	public String save(UserDetailDto dto, RedirectAttributes redirectAttrs) throws UserException {
		try {
			dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
			redirectAttrs.addFlashAttribute("user", userService.save(dto, redirectAttrs).block());
			return "redirect:/";
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", e instanceof AbstractBusinessException ? e.getMessage() : "Erro ao tentar salvar");
			redirectAttrs.addFlashAttribute("user", dto);
			return "redirect:/usuario/cadastro";
		}
	}

//	@DeleteMapping("/exemplo/{userId}/delete")
//	@ApiOperation(value = "Exclusão de usuário")
//	@ResponseBody
//	public String delete(@PathVariable("userId") Long userId) {
//		try {
//			userService.delete(userId);
//		} catch (UserException e) {
//			return e.getMessage();
//		}
//		return "OK";
//	}
}
