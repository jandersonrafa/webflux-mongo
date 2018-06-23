package com.article.article.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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
public class EventDetailDto {

	private String eventId;

	@NotBlank(message = "Nome evento é obrigatório")
	private String eventName;

	@NotNull(message = "Data evento é obrigatório")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate eventDate;

	@NotNull(message = "Data abertura inscrição")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate registrationStartDate;

	@NotNull(message = "Data encerramento inscrição")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate registrationEndDate;

	private List<EventArticleDetailDto> articles;

}
