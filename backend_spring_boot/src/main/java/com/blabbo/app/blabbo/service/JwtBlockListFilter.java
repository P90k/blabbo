package com.blabbo.app.blabbo.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtBlockListFilter extends OncePerRequestFilter {

    private final JwtTokenBlockListService jwtTokenBlockListService;
    private final JwtDecoder jwtDecoder;


    public JwtBlockListFilter(JwtTokenBlockListService jwtTokenBlockListService,
                              JwtDecoder jwtDecoder) {
        this.jwtTokenBlockListService = jwtTokenBlockListService;
        this.jwtDecoder               = jwtDecoder;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws
                                                             ServletException,
                                                             IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            Jwt jwt = this.jwtDecoder.decode(token);

            String jti = jwt.getClaimAsString("jti");

            if (jti != null && jwtTokenBlockListService.isTokenBlocked(jti)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token is revoked");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
