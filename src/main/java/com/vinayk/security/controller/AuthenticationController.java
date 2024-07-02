package com.vinayk.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinayk.security.AuthenticationService;
import com.vinayk.security.model.AuthenticationRequest;
import com.vinayk.security.model.RequestAuth;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private  final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationRequest> register(
            @RequestBody RequestAuth requestAuth
    ) {
        return ResponseEntity.ok(authenticationService.register(requestAuth));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationRequest> authenticate(
            @RequestBody RequestAuth requestAuth
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(requestAuth));
    }

}
