package com.article.article.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Janderson
 */
@Document(collection = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

	@Id
	private String eventId;

	@NotBlank
	private String eventName;

	@NotNull
	private LocalDate eventDate;

	@NotNull
	private LocalDate registrationStartDate;

	@NotNull
	private LocalDate registrationEndDate;

	@DBRef
	private User user;

}
