package com.article.article.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

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

	@NotNull(message = "Arquivo é obrigatório")
	private MultipartFile file;

	private String fileName;

	private Event event;

}
