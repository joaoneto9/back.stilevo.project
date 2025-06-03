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

    @NotBlank(message = "erro, formato do logradouro invalido")
    private String logradouro;

    @NotBlank(message = "erro, complemneto nulo")
    private String complemento;

    @NotBlank(message = "erro, bairro nulo")
    private String bairro;

    @NotBlank(message = "erro, formato da localidade invalido")
    private String localidade;

    @NotBlank(message = "erro, formato do UF invalido")
    private String uf;

    @NotNull(message = "erro, numero nulo")
    private Integer numero;

    private String pontoReferencia;

}
