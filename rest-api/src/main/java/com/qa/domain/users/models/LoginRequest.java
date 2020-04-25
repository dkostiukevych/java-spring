package com.qa.domain.users.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@SuppressWarnings("Javadoctype")
public class LoginRequest {
    
    @NotBlank
    private String username;

    @NotBlank
    private String password;
    
}
