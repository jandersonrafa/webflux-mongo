package com.article.article.controller;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.article.article.repository.UserRepository;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

/**
 *
 * @author Janderson
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/exemplo")
public class ExemploController {

	private final UserRepository userRepository;

	@ApiOperation(value = "Exemplo SSE")
	@ResponseBody
	@GetMapping(value = "sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> get() {
		return userRepository.findAll().delayElements(Duration.ofSeconds(2)).map(r -> r.getEmail());
	}

	@GetMapping("/sse-ssemiter")
	@ApiOperation(value = "Exemplo SSE com SSemiter")
	public SseEmitter enableNotifier() {
		SseEmitter sseEmitter = new SseEmitter(86400000L);
		CompletableFuture.runAsync(() -> {
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(2000);
					sseEmitter.send("contador" + i);
				} catch (Exception ex) {
					Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		return sseEmitter;
	}

}
