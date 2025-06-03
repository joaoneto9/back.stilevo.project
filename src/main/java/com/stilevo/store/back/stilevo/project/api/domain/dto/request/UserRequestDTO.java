package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

import com.stilevo.store.back.stilevo.project.api.domain.entity.embeddable.Endereco;
import com.stilevo.store.back.stilevo.project.api.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "erro, o nome esta vazio")
    private String name;

    @Email(message = "erro, o formato do email esta invalido")
    private String email;

    @NotBlank(message = "erro, a senha esta vazia")
    private String password;

    @NotNull(message = "erro, a role do usuario esta nula")
    private UserRole role;

    private EnderecoRequestDTO endereco;

}
