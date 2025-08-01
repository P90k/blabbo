package com.blabbo.app.blabbo.service;

import com.blabbo.app.blabbo.exceptions.InvalidJwtException;
import com.blabbo.app.blabbo.service.interfaces.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class AuthServiceImpl implements AuthService {
    final JwtTokenBlockListService jwtTokenBlockListService;
    private final JwtDecoder jwtDecoder;


    public AuthServiceImpl(JwtTokenBlockListService jwtTokenBlockListService,
                           JwtDecoder jwtDecoder) {
        this.jwtTokenBlockListService = jwtTokenBlockListService;
        this.jwtDecoder               = jwtDecoder;
    }

/*
    @Override
    public String token() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        Instant now = Instant.now();
        long expiry = 900;

        /*
        String scope = authentication.getAuthorities()
                                     .stream()
                                     .map(GrantedAuthority::getAuthority)
                                     .collect(Collectors.joining(" "));



        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                                                .issuer("self")
                                                .issuedAt(now)
                                                .expiresAt(
                                                        now.plusSeconds(expiry))
                                                .subject(
                                                        authentication.getName())
                                                .id(UUID.randomUUID()
                                                        .toString())
                                                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet))
                         .getTokenValue();
    }
*/
    @Override
    public String logout(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            Jwt jwt = jwtDecoder.decode(token);
            String jti = jwt.getClaimAsString("jti");

            if (jti != null) {
                Instant exp = jwt.getExpiresAt();
                if (exp == null) {
                    throw new InvalidJwtException("JWT expiry field missing");
                }
                long ttl = (exp.toEpochMilli() - Instant.now().toEpochMilli()) /
                        1000;
                if (ttl < 0) {
                    throw new InvalidJwtException("Invalid JWT: Expired");
                }
                jwtTokenBlockListService.blockToken(jti, ttl);
                return "Logged out successfully";
            }
        }
        throw new InvalidJwtException("Invalid Authorization header.");

    }
}
