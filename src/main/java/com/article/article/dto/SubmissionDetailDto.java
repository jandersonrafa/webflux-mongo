package com.article.article.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.article.article.model.Event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Janderson
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionDetailDto {

	private String articleId;

	@NotBlank(message = "Titulo é obrigatório")
	private String title;

	@NotBlank(message = "Resumo é obrigatório")
	private String resume;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate submissionDate;

	private String fileName;

	private Event event;

}
