package com.vinayk.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
public class AppController {

    @GetMapping("/demo")
    public String demo() {
        return "Hello from secured api";
    }

}
