package com.article.article.model;

import java.math.BigInteger;
import java.time.LocalDate;

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
@Document(collection = "article")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

	@Id
	private BigInteger articleId;

	@NotNull
	private String title;

	@NotNull
	private String resume;

	@NotNull
	private LocalDate submissionDate;

	@NotNull
	private String fileName;

//	@ManyToOne
//	@JoinColumn(name = "user_id")
	@DBRef
	private User user;

//	@ManyToOne
//	@JoinColumn(name = "event_id")
//	private Event event;
}
