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
import com.article.article.exception.AbstractBusinessException;
import com.article.article.service.user.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
@RequestMapping("/usuario")
public class UserController {

	private final UserService userService;

	@GetMapping("/{userId}")
	@ApiOperation(value = "Exibe dados do usuário")
	public Mono<ModelAndView> exibeUsuario(@PathVariable("userId") String userId) {
		return userService.findById(userId).map(user -> new ModelAndView("user/userDetail", "user", user));
	}

//
//	@GetMapping("/cadastro")
//	@ApiOperation(value = "Exibe formulario cadastro de usuário")
//	public ModelAndView pageRegisterUser() {
//		ModelAndView mv = new ModelAndView("user/show");
//		mv.addObject("userRegister", "");
//		return mv;
//	}
//
//	@PostMapping
//	@ApiOperation(value = "Inserção de dados de usuario")
//	@ResponseBody
//	public String save(@RequestBody UserInput userInput) throws UserException {
//		userInput.setId(null);
//		try {
//			userService.save(userInput, false);
//		} catch (UserException e) {
//			return e.getMessage();
//		}
//		return "OK";
//	}
//
	@PostMapping("{id}")
//	@ResponseBody
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
