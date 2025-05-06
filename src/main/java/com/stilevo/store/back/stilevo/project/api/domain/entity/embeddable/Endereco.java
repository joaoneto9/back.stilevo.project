package com.stilevo.store.back.stilevo.project.api.domain.entity.embeddable;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Endereco {

    private String street;
    private String number;
    private String city;
    private String state;
    private String cep;

}
