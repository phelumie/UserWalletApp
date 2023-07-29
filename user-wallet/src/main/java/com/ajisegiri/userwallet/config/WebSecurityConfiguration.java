package com.ajisegiri.userwallet.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true,jsr250Enabled = true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .sessionManagement((sessionManagement) ->
                                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/actuator/**","/webjars/**","/swagger-ui*/**",
                                        "/v3/api-docs/**","/error").permitAll())
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                 .anyRequest().authenticated())
                .oauth2ResourceServer((oauth2ResourceServer) ->
                        oauth2ResourceServer
                                .jwt(jwtConfigurer ->jwtAuthenticationConverter()));

        return  http.build();
    }
    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt ->
                Optional.ofNullable(jwt.getClaimAsStringList("custom_claims"))
                        .stream()
                        .flatMap(Collection::stream)
                        .map(roleName -> "ROLE_" + roleName) // prefix required by Spring Security for roles.  remove line if using @PreAuthorize("hasAuthority('USER')")
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
        return converter;
    }

}