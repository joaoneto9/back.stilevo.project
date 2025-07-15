package com.stilevo.store.back.stilevo.project.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.stilevo.store.back.stilevo.project.api.domain.entity.User;
import com.stilevo.store.back.stilevo.project.api.exception.GeneratingTokenException;
import com.stilevo.store.back.stilevo.project.api.exception.InvalidAuthenticationUserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // isso vem junto da dependencia do JWT
            return JWT.create() //cria
                    .withIssuer("auth-api") // emissor do token
                    .withSubject(user.getEmail()) // usuario que esta recebendo esse token
                    .withExpiresAt(genExpirationDate()) //tempo de expiracaco do token, um Instant
                    .sign(algorithm); // realiza a assinatura

        } catch (JWTCreationException e) { //excessao quando ano conseguir criar o token
            throw new GeneratingTokenException("ERROR GENERATING TOKEN:");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm) // requisita esse algotimo
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)//verifica o token
                    .getSubject(); // pegou o subject que foi criado ao criar o token
        } catch (JWTVerificationException e) {
            throw new InvalidAuthenticationUserException("ERRO AO VALIDAR O TOKEN");

        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime
                .now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00")); // duas horas depois de agora, timezone do Brasil
    }
}
