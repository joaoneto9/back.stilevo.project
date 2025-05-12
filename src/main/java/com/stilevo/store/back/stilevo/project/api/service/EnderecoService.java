package com.stilevo.store.back.stilevo.project.api.service;


import com.stilevo.store.back.stilevo.project.api.controller.exception.InvallidCepException;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.EnderecoRequestDTO;
import com.stilevo.store.back.stilevo.project.api.domain.dto.request.EnderecoViacepRequest;
import com.stilevo.store.back.stilevo.project.api.domain.entity.embeddable.Endereco;
import com.stilevo.store.back.stilevo.project.api.mapper.EnderecoMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

    private static final String URL = "https://viacep.com.br/ws/{cep}/json/";

    // realiza a requisicao para pegar o CEP
    public EnderecoViacepRequest getCep(String cep) {
        try {
            EnderecoViacepRequest enderecoViacepRequest = new RestTemplate().getForObject(URL, EnderecoViacepRequest.class, cep);

            if (Boolean.TRUE.equals(enderecoViacepRequest.getErro())) // se for null, retorna false
                throw new InvallidCepException("cep nao existe, erro ao buscar endereco");

            return enderecoViacepRequest;
        } catch (RestClientException e) {
            throw new InvallidCepException("cep com formato invalido, erro ao buscar endereco!");
        }
    }
}
