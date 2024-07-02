package com.vinayk.security.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RequestAuth {
    private String username, password, email;
}
