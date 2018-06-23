package com.article.article.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.article.article.dto.SubmissionDetailDto;
import com.article.article.dto.output.SubmissionListingDto;
import com.article.article.exception.AbstractBusinessException;
import com.article.article.model.Event;
import com.article.article.service.article.ArticleService;
import com.google.common.base.Strings;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
@RequestMapping("/submissoes")
public class SubmissionController {

	private final ArticleService articleService;

	@GetMapping
	@ApiOperation(value = "Exibe pagina com listagem de eventos que usuario submeteu artigos")
	public ModelAndView pageEvent() {
		return new ModelAndView("submission/submissionList");
	}

	@ResponseBody
	@GetMapping(value = "/todos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ApiOperation(value = "listagem de eventos que usuario submeteu artigo")
	public Flux<SubmissionListingDto> findEventWithArticleLoggedUser() {
		return articleService.findEventWithArticleLoggedUser();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Exibe detalhamento da submissao")
	public Mono<ModelAndView> pageDetail(@PathVariable("id") String articleId, @ModelAttribute("submission") SubmissionDetailDto dto) {
		return Strings.isNullOrEmpty(dto.getArticleId()) ? articleService.findById(articleId).map(submission -> new ModelAndView("submission/submissionDetail", "submission", submission)) : Mono.just(new ModelAndView("submission/submissionDetail", "submission", dto));
	}

	@GetMapping("/{id}/delete")
	@ApiOperation(value = "Exclusão de evento")
	public String delete(@PathVariable("id") String submissionId) {
		articleService.delete(submissionId);
		return "redirect:/submissoes";
	}

	@PostMapping("{id}")
	@ApiOperation(value = "Update de artigo submetido")
	public String update(@PathVariable("id") String submissionId, SubmissionDetailDto dto, RedirectAttributes redirectAttrs) {
		try {
			dto.setArticleId(submissionId);
			// TODO redirecionar para evento quando sucesso
			redirectAttrs.addFlashAttribute("submission", articleService.update(submissionId, dto).block());
			return "redirect:/submissoes/" + submissionId;
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", e instanceof AbstractBusinessException ? e.getMessage() : "Erro ao tentar salvar");
			redirectAttrs.addFlashAttribute("event", dto);
			return "redirect:/submissoes/" + submissionId;
		}
	}

	@PostMapping("/evento/{idEvento}")
	@ApiOperation(value = "Cria nova Submissao de artigo")
	public String save(@PathVariable("idEvento") String idEvento, SubmissionDetailDto dto, RedirectAttributes redirectAttrs) {
		try {
			// TODO redirecionar para evento quando sucesso
			SubmissionDetailDto dtoSave = articleService.create(dto, idEvento).block();
			redirectAttrs.addFlashAttribute("submission", dtoSave);
			return "redirect:/submissoes/" + dtoSave.getArticleId();
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", e instanceof AbstractBusinessException ? e.getMessage() : "Erro ao tentar salvar");
			redirectAttrs.addFlashAttribute("submission", dto);
			return "redirect:/submissoes/evento/" + idEvento;
		}
	}

	@GetMapping("/evento/{idEvento}")
	@ApiOperation(value = "Formulario submissao de artigo")
	public ModelAndView pageSubmission(@PathVariable("idEvento") String idEvento, @ModelAttribute("submission") SubmissionDetailDto submissionDetailDto) {
		final SubmissionDetailDto dto = new SubmissionDetailDto();
		dto.setEvent(Event.builder().eventId(idEvento).build());
		submissionDetailDto.setEvent(Event.builder().eventId(idEvento).build());
		return Strings.isNullOrEmpty(submissionDetailDto.getArticleId()) ? new ModelAndView("submission/submissionDetail", "submission", dto) : new ModelAndView("submission/submissionDetail", "submission", submissionDetailDto);
	}
}
