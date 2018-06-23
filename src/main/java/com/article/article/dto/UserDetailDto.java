package com.article.article.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(description = "Informações de Usuário")
public class UserDetailDto {

	@JsonIgnore
	@ApiModelProperty(example = "1", notes = "Identificador")
	private String id;

	@NotBlank(message = "Usuário é obrigatório")
	@ApiModelProperty(example = "user1", notes = "Username")
	private String username;

	@NotBlank(message = "Email é obrigatório")
	@ApiModelProperty(example = "user@dominio.com", notes = "E-mail de contato")
	private String email;

	@NotBlank(message = "Nome é obrigatório")
	@ApiModelProperty(example = "Joao Fulano", notes = "Nome do usuário")
	private String name;

	@ApiModelProperty(example = "password1", notes = "Senha para login")
	@NotBlank(message = "Senha é obrigatório")
	private String password;

	@NotBlank(message = "Confirmação Senha é obrigatório")
	@ApiModelProperty(example = "password1", notes = "Confirmação de senha para cadastro/atualização")
	private String passwordConfirm;

}
