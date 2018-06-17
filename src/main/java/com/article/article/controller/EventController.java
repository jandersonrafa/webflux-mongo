package com.article.article.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.article.article.dto.output.EventListingDto;
import com.article.article.service.event.EventService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
@RequestMapping("/evento")
public class EventController {

	private final EventService eventService;

	@GetMapping
	@ApiOperation(value = "Exibe dados do usuário")
	public ModelAndView exibeUsuario() {
		return new ModelAndView("event/eventList");
	}

	@ResponseBody
	@GetMapping(value = "/todos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ApiOperation(value = "listagem de eventos")
	public Flux<EventListingDto> pageEvent() {
		return eventService.findAll();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Exibe detalhamento de evento")
	public Mono<ModelAndView> pageDetail(@PathVariable("id") String eventId) {
		return eventService.findById(eventId).map(event -> new ModelAndView("event/eventDetail", "event", event));
	}
//
//	@GetMapping("/{id}/delete")
//	@ApiOperation(value = "Exclusão de evento")
//	@ResponseBody
//	public String delete(@PathVariable("id") Long eventId) {
//		return "OK";
//	}
//
//	@PostMapping
//	@ApiOperation(value = "Inserção de dados de evento")
//	@ResponseBody
//	public String save(@RequestBody Object input) {
//		return "OK";
//	}
//
//	@PostMapping("{id}")
//	@ApiOperation(value = "Update de evento")
//	@ResponseBody
//	public String update(@PathVariable("id") Long eventId, @RequestBody Object input) {
//		return "OK";
//	}
}
