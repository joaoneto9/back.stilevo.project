package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnderecoRequestDTO {

    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP deve estar no formato 00000-000")
    private String cep;

    @NotBlank
    private String logradouro;

    @NotNull
    private String complemento;

    @NotNull
    private String bairro;

    @NotBlank
    private String localidade;

    @NotBlank
    private String uf;

    @NotNull
    private Integer numero;

    private String pontoReferencia;

}
