package com.stilevo.store.back.stilevo.project.api.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDTO {

    @NotBlank(message = "Email do destintario e obrigatorio")
    @Email(message = "Formato de e-mail inválido")
    private String userEmail;

    @NotBlank(message = "Assunto do Email e obrigatorio")
    private String title;

    @NotBlank(message = "O conteúdo do e-mail é obrigatório")
    private String content;


}
