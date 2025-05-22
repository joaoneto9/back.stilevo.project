package com.stilevo.store.back.stilevo.project.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements WebMvcConfigurer {

    private final SecurityFilter securityFilter;

    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .cors(Customizer.withDefaults()) // ativa o CORS
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .csrf(AbstractHttpConfigurer::disable) // csrf e desativado, pois ele nao e normalmente ativado para APIS REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // determina a politica de sessao
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers( "/h2-console/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/users/POST/register").permitAll() // permite que todos podem registar usuario("apenas teste")
                                .requestMatchers(HttpMethod.POST, "/api/auth/POST/login").permitAll() // permite que todos podem logar
                                .requestMatchers(HttpMethod.POST, "/api/products/variation/POST/save").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/products/variation/DELETE/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/products/variation/UPDATE/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/products/DELETE/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/products/UPDATE/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/products/POST/save").hasRole("ADMIN") // isso quer dizer que apensas os usuarios com role 'admin' estao autorizados para dar um POST com o endpoint '/product/save'
                                .requestMatchers(HttpMethod.GET, "/api/users/GET/all").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/DELETE/{id}").hasRole("ADMIN")
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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("*");
    }

}
