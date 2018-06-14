package com.article.article.controller;

import java.math.BigInteger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.article.article.service.UserService;

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
	public Mono<ModelAndView> exibeUsuario(@PathVariable("userId") BigInteger userId) {
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
//	@PutMapping("{id}")
//	@ApiOperation(value = "Alteração de dados de usuario")
//	@ResponseBody
//	public String update(@PathVariable("id") Long userId, @RequestBody UserInput userInput) throws UserException {
//		userInput.setId(userId);
//		try {
//			userService.save(userInput, true);
//		} catch (UserException e) {
//			return e.getMessage();
//		}
//		return "OK";
//	}
//
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
