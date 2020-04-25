package com.qa.auth.jwt.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@SuppressWarnings("JavadocType")
public class ApiResponse {

    private Boolean success;
    private String message;
    
}
