package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchRequestDTO {

    private String name;

    @Email(message = "Email invalido") // dispara o erro, menos se for null
    private String email;

    private String password;

    private EnderecoRequestDTO endereco;
}
