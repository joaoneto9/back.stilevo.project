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

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
    @NotNull
    private UserRole role;


    private EnderecoRequestDTO endereco;

}
