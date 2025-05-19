package com.stilevo.store.back.stilevo.project.api.domain.dto.response;
import com.stilevo.store.back.stilevo.project.api.domain.entity.embeddable.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private EnderecoResponseDTO endereco;

}
