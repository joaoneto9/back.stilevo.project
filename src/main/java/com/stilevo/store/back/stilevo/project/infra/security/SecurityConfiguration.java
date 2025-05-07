package com.stilevo.store.back.stilevo.project.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // csrf e desativado, pois ele nao e normalmente ativado para APIS REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // determina a politica de sessao
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/api/users/POST/register").permitAll() // permite que todos podem registar usuario("apenas teste")
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // permite que todos podem logar
//                                .requestMatchers(HttpMethod.POST, "/product/save").hasRole("ADMIN") // isso quer dizer que apensas os usuarios com role 'admin' estao autorizados para dar um POST com o endpoint '/product/save'
                                .anyRequest().authenticated() // isso quer dizer que qualquer outra requisicao que for feita precisa de autenticacao
                        )
                .build(); // cria
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // retorna um AuthenticationManager
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // retorna uma classe que permite criptografar ou decriptografar uma String
    }

}
