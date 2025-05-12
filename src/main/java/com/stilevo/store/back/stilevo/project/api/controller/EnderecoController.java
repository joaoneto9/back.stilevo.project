package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.response.EnderecoResponseDTO;
import com.stilevo.store.back.stilevo.project.api.mapper.EnderecoMapper;
import com.stilevo.store.back.stilevo.project.api.service.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/endereco")
public class EnderecoController {

    private final EnderecoMapper enderecoMapper;

    public EnderecoController(EnderecoMapper enderecoMapper, EnderecoService enderecoService) {
        this.enderecoMapper = enderecoMapper;
    }

    @GetMapping(value = "/GET/via/cep/{cep}") // ele so busca e mostra o CEP
    public ResponseEntity<EnderecoResponseDTO> getEnderecoViaCep(@PathVariable String cep) {
        return ResponseEntity.ok(enderecoMapper.toResponse(EnderecoService.getCep(cep)));
    }

}
