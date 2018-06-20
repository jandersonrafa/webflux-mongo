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
@Document(collection = "article")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article implements Comparable<Article> {

	@Id
	private String articleId;

	@NotBlank
	private String title;

	@NotBlank
	private String resume;

	@NotNull
	private LocalDate submissionDate;

	@NotBlank
	private String fileName;

//	@ManyToOne
//	@JoinColumn(name = "user_id")
	@NotNull
	@DBRef
	private User user;

	@NotNull
	@DBRef
	private Event event;

//	@ManyToOne
//	@JoinColumn(name = "event_id")
//	private Event event;
	@Override
	public int compareTo(Article o) {
		return this.getSubmissionDate().compareTo(o.getSubmissionDate());
	}
}
