package com.stilevo.store.back.stilevo.project.api.config.security;

import com.stilevo.store.back.stilevo.project.api.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter { // usa-se a cada requiiscao
    //filtra antes de acontecer as filtragens de permissao da configuracaco

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);
        if (token == null) { // isso significa que nao precisa de token... assim ele passa para o filtro normal, o que indica que roda os "permitAll" normalmente
            filterChain.doFilter(request, response); // se nao tiver token, chama o proximo filtro na config
            return; // isso evita que erealiza a verificacao, pois o token e null
        }

        var subject = tokenService.validateToken(token); // valida o token
        UserDetails user = userRepository.findByEmail(subject); // pega o usuario caso nao tenha dado o erro
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request, response); // repassa para o filtro natural, que ve que precisa authenticar e esta authenticado
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) return null; // nao tem token nessa requisicao

        return authHeader.replace("Bearer ", "");
    }

}
