package com.vinayk.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vinayk.security.Repository.UserRepository;
import com.vinayk.security.authentication.JwtService;
import com.vinayk.security.model.AuthenticationRequest;
import com.vinayk.security.model.RequestAuth;
import com.vinayk.security.model.Role;
import com.vinayk.security.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationRequest register(RequestAuth requestAuth) {

        User user = User.builder().username(requestAuth.getUsername())
                .password(passwordEncoder.encode(requestAuth.getPassword()))
                .email(requestAuth.getEmail()).role(Role.USER).build();
        userRepository.save(user);
        final String token = jwtService.generateToken(user);
        return AuthenticationRequest.builder().accessToken(token).build();
    }

    public AuthenticationRequest authenticate(RequestAuth requestAuth) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestAuth.getUsername(),
                        requestAuth.getPassword()));
        User user = User.builder().username(requestAuth.getUsername())
                .password(passwordEncoder.encode(requestAuth.getPassword()))
                .email(requestAuth.getEmail()).build();
        final String token = jwtService.generateToken(user);
        return AuthenticationRequest.builder().accessToken(token).build();
    }
}
