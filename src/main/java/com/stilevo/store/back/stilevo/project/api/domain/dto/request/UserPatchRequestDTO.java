package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchRequestDTO {

    private String name;

    @NotBlank(message = "senha e necessaria para atualizar dados")
    private String password;

    private EnderecoRequestDTO endereco;
}
