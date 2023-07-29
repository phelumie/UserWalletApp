package com.ajisegiri.usermanagement.controller;

import com.ajisegiri.usermanagement.dto.CreateUser;
import com.ajisegiri.usermanagement.dto.LoginDto;
import com.ajisegiri.usermanagement.dto.LoginPayload;
import com.ajisegiri.usermanagement.service.AuthService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("signup")
    public ResponseEntity<LoginPayload> signUp(@Valid @RequestBody CreateUser user){
        log.info("creating user with email address {}",user.getUser().getEmail());
        var result=authService.createUser(user);
        return ResponseEntity.ok(result);
    }


    @PostMapping("login")
    public ResponseEntity<LoginPayload> login(@RequestBody LoginDto loginDto){
        var result=authService.login(loginDto);
        return ResponseEntity.ok(result);
    }


}
