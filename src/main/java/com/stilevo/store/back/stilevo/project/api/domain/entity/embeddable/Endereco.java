package com.stilevo.store.back.stilevo.project.api.domain.entity.embeddable;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Endereco {

    private String street;
    private String number;
    private String city;
    private String state;
    private String cep;

}
