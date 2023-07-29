package com.ajisegiri.usermanagement.service;

import com.ajisegiri.usermanagement.model.User;
import com.ajisegiri.usermanagement.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> findAll(){
        return userRepository.findAll().stream().map(s->{
            s.setPassword(null);
        return s;}).toList();
    }

}
