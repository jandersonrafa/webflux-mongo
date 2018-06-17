package com.article.article.dto;

import javax.validation.constraints.NotBlank;

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
public class UserDetailDto {

	private String id;

	@NotBlank(message = "Usuário obrigatório")
	private String username;

	@NotBlank(message = "Email obrigatório")
	private String email;

	@NotBlank(message = "Nome obrigatório")
	private String name;

}
