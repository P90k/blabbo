package com.blabbo.app.blabbo.controller;

import com.blabbo.app.blabbo.service.JwtTokenBlockListService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final JwtTokenBlockListService jwtTokenBlockListService;


    public AuthController(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder,
                          JwtTokenBlockListService jwtTokenBlockListService) {
        this.jwtEncoder               = jwtEncoder;
        this.jwtDecoder               = jwtDecoder;
        this.jwtTokenBlockListService = jwtTokenBlockListService;
    }


    @PostMapping("/login")
    String token(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 36000L;

        String scope = authentication.getAuthorities()
                                     .stream()
                                     .map(GrantedAuthority::getAuthority)
                                     .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .issuer("self")
                                          .issuedAt(now)
                                          .expiresAt(now.plusSeconds(expiry))
                                          .subject(authentication.getName())
                                          .id(UUID.randomUUID().toString())
                                          .claim("scope", scope)
                                          .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims))
                              .getTokenValue();
    }


    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            Jwt jwt = jwtDecoder.decode(token);
            String jti = jwt.getClaimAsString("jti");

            if (jti != null) {
                Instant exp = jwt.getExpiresAt();
                if (exp == null) {
                    return "Invalid token, missing expiration";
                }
                long ttl = (exp.toEpochMilli() - Instant.now().toEpochMilli()) /
                        1000;
                if (ttl < 0) {
                    return "Token already expired";
                }
                jwtTokenBlockListService.blockToken(jti, ttl);
                return "Logged out successfully";
            }
        }
        return "Invalid Authorization header.";
    }


}
