package com.stilevo.store.back.stilevo.project.api.config.security;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // csrf e desativado, pois ele nao e normalmente ativado para APIS REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // determina a politica de sessao
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/api/users/POST/register").permitAll() // permite que todos podem registar usuario("apenas teste")
                                .requestMatchers(HttpMethod.POST, "/api/users/POST/login").permitAll() // permite que todos podem logar
                                .requestMatchers(HttpMethod.POST, "/api/products/variation/POST/save").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/product/variation/DELETE/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/products/variation/UPDATE/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/products/POST/save").hasRole("ADMIN") // isso quer dizer que apensas os usuarios com role 'admin' estao autorizados para dar um POST com o endpoint '/product/save'
                                .requestMatchers(HttpMethod.GET, "/api/users/GET/all").hasRole("ADMIN")
                                .anyRequest().authenticated() // isso quer dizer que qualquer outra requisicao que for feita precisa de autenticacao
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // adiciona um filtro antes da verificaco
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
