package com.article.article.controller;

import java.util.Optional;

import org.omg.CORBA.UserException;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.article.article.dto.UserDetailDto;
import com.article.article.dto.output.UserListingDto;
import com.article.article.exception.AbstractBusinessException;
import com.article.article.exception.ValidateException;
import com.article.article.service.user.UserService;
import com.google.common.base.Strings;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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

	@GetMapping("/{userId}")
	@ApiOperation(value = "Exibe pagina detalhes do usuário")
	public Mono<ModelAndView> exibeUsuario(@PathVariable("userId") String userId, @ModelAttribute("user") UserDetailDto dto) {
		return Strings.isNullOrEmpty(dto.getId()) ? userService.findById(userId).map(user -> new ModelAndView("user/userDetail", "user", user)) : Mono.just(new ModelAndView("user/userDetail", "user", dto));
	}

	@PostMapping("/{id}")
	@ApiOperation(value = "Alteração de dados de usuario")
	public String update(@PathVariable("id") String userId, UserDetailDto dto, RedirectAttributes redirectAttrs) throws UserException {
		try {
			// TODO redirecionar para evento quando sucesso
			dto.setId(userId);
			handlePassword(dto);
			redirectAttrs.addFlashAttribute("user", userService.update(userId, dto).block());
			return "redirect:/evento";
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", e instanceof AbstractBusinessException ? e.getMessage() : "Erro ao tentar salvar");
			redirectAttrs.addFlashAttribute("user", dto);
			return "redirect:/usuario/" + userId;
		}
	}

	@GetMapping("/cadastro")
	@ApiOperation(value = "Exibe pagina cadastro de usuário")
	public ModelAndView pageRegister(@ModelAttribute("user") UserDetailDto dto) {
		return new ModelAndView("user/userDetail", "user", dto);
	}

	@PostMapping("/cadastro")
	@ApiOperation(value = "Salvar usuario")
	public String save(UserDetailDto dto, RedirectAttributes redirectAttrs) throws UserException {
		try {
			handlePassword(dto);
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
	private void handlePassword(UserDetailDto dto) throws ValidateException {
		if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
			throw new ValidateException("Senha e confirmação de senha não são iguais.");
		}
		Optional.ofNullable(dto.getPassword()).ifPresent(password -> {
			dto.setPassword(bCryptPasswordEncoder.encode(password));
		});
		Optional.ofNullable(dto.getPasswordConfirm()).ifPresent(password -> {
			dto.setPasswordConfirm(bCryptPasswordEncoder.encode(password));
		});
	}
}
