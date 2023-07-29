package com.ajisegiri.usermanagement.controller;

import com.ajisegiri.usermanagement.model.User;
import com.ajisegiri.usermanagement.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("users")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<User>> getUsers(Principal principal){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

}
