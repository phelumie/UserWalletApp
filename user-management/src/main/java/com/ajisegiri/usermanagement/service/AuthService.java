package com.ajisegiri.usermanagement.service;

import com.ajisegiri.usermanagement.dto.*;
import com.ajisegiri.usermanagement.enums.Currency;
import com.ajisegiri.usermanagement.model.User;
import com.ajisegiri.usermanagement.repo.UserRepository;
import com.ajisegiri.usermanagement.webclient.FirebaseClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ajisegiri.usermanagement.config.RabbitMQDirectConfig.WALLET_QUEUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final FirebaseAuth firebaseAuth;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FirebaseClient firebaseClient;
    private final EventPublisher eventPublisher;
    private static final String SIGN_IN ="https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";
    @Value("${firebase.apikey}")
    private String apikey;
    private final ModelMapper modelMapper;

    @SneakyThrows
    @Transactional
    public LoginPayload createUser(CreateUser createUser){
        var password=createUser.getUser().getPassword();
        var body=new UserRecord.CreateRequest();
        body.setPassword(createUser.getUser().getPassword());
        body.setEmail(createUser.getUser().getEmail());
        body.setEmailVerified(true);
        var response=firebaseAuth.createUser(body);

        var user=convertUserDtoToEntity(createUser.getUser());

        user.setPassword(passwordEncoder.encode(password));
        user.setFireBaseUid(response.getUid());
        User saved;
        try {
            user=userRepository.save(user);
            firebaseAuth.setCustomUserClaims(response.getUid(),Map.of("custom_claims", List.of(user.getRole().toString())));
        } catch (Exception exception){
            firebaseAuth.deleteUser(response.getUid());
            throw new IllegalStateException(exception.getMessage());
        }
        eventPublisher.publish(new WalletPayload(user.getId(), Currency.NGN),WALLET_QUEUE);
        log.info("successfully created user with email address {}",user.getEmail());
        return getToken(user.getEmail(),password);
    }



    public LoginPayload login(LoginDto loginDto){
        return getToken(loginDto.email(),loginDto.password());
    }

    private LoginPayload getToken(String email, String password){
        log.info("Trying to log in user with email address {}",email);
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);
        payload.put("returnSecureToken", true);

        var uri= UriComponentsBuilder.
                fromUriString(SIGN_IN).queryParam("key",apikey)
                .build()
                .toUri();

        return firebaseClient.signIn(uri,payload);

    }
    private User convertUserDtoToEntity(UserDto userDto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        User user = modelMapper.map(userDto,User.class);
        return user;

    }

}
