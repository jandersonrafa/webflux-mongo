package com.article.article.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.article.article.repository.UserRepository;

import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/usuario")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@ApiOperation(value = "Lista todos usuarios")
	@ResponseBody
	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> get() {
		return userRepository.findAll().delayElements(Duration.ofSeconds(2)).map(r -> r.getEmail());
	}
}
