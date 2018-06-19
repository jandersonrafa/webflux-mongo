package com.article.article.dto;

import java.time.LocalDate;

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

	private String eventName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate eventDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate registrationStartDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate registrationEndDate;

}
