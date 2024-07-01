package com.vinayk.security.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthenticationRequest {
    private String username, password, email;
}
