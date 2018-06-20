package com.article.article.dto;

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
    private Long id;
    @ApiModelProperty(example = "user1", notes = "Username")
    private String username;
    @ApiModelProperty(example = "user@dominio.com", notes = "E-mail de contato")
    private String email;
    @ApiModelProperty(example = "Joao Fulano", notes = "Nome do usuário")
    private String name;
    @ApiModelProperty(example = "password1", notes = "Senha para login")
    private String password;
    @ApiModelProperty(example = "password1", notes = "Confirmação de senha para cadastro/atualização")
    private String passwordConfirm;

}
