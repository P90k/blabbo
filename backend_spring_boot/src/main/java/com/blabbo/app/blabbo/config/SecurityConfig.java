package com.blabbo.app.blabbo.config;

import com.blabbo.app.blabbo.security.filters.JwtBlockListFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
public class SecurityConfig {





    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtBlockListFilter jwtBlockListFilter) throws
                                                                                          Exception {
        http.authorizeHttpRequests(
                    (requests) -> requests.requestMatchers("api/users/create")
                                          .permitAll()
                                          .anyRequest()
                                          .authenticated())
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtBlockListFilter,
                             UsernamePasswordAuthenticationFilter.class)
            .oauth2ResourceServer((jwt) -> jwt.jwt(Customizer.withDefaults()))
            .sessionManagement((session) -> session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS))
            .exceptionHandling(
                    (exceptions) -> exceptions.authenticationEntryPoint(
                                                      new BearerTokenAuthenticationEntryPoint())
                                              .accessDeniedHandler(
                                                      new BearerTokenAccessDeniedHandler()));
        return http.build();
    }


    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



    /*

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks =
                new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }


 */


}