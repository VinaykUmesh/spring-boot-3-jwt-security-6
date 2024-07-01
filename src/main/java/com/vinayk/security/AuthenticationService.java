package com.vinayk.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vinayk.security.Repository.UserRepository;
import com.vinayk.security.authentication.JwtService;
import com.vinayk.security.model.AuthenticationResponse;
import com.vinayk.security.model.AuthenticationRequest;
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

    public AuthenticationResponse register(AuthenticationRequest authenticationRequest) {

        User user = User.builder().username(authenticationRequest.getUsername())
                .password(passwordEncoder.encode(authenticationRequest.getPassword()))
                .email(authenticationRequest.getEmail()).role(Role.USER).build();
        userRepository.save(user);
        final String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(token).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
        User user = User.builder().username(authenticationRequest.getUsername())
                .password(passwordEncoder.encode(authenticationRequest.getPassword()))
                .email(authenticationRequest.getEmail()).build();
        final String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(token).build();
    }
}
