package com.example.demo.controller.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class RegisterRequest {
    private String username;
    private String email;
    private int age;
    private String password;
}
