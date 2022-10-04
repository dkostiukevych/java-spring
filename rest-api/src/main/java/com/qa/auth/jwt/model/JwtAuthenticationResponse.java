package com.qa.auth.jwt.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("JavadocType")
public class JwtAuthenticationResponse {
    
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
