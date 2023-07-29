package com.ajisegiri.usermanagement.webclient;

import com.ajisegiri.usermanagement.dto.LoginPayload;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.net.URI;

@HttpExchange(accept = "application/json", contentType = "application/json")
public interface FirebaseClient {

    @PostExchange
    LoginPayload signIn(URI uri, @RequestBody Object payload);


}
