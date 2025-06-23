package com.stilevo.store.back.stilevo.project.api.controller;

import com.stilevo.store.back.stilevo.project.api.domain.dto.request.EmailRequestDTO;
import com.stilevo.store.back.stilevo.project.api.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Void> enviarEmail(@Valid @RequestBody EmailRequestDTO emailData) {
        emailService.sendEmail(emailData);
        return ResponseEntity.noContent().build();
    }
}
