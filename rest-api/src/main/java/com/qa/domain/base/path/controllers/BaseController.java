package com.qa.domain.base.path.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("/")
@SuppressWarnings("JavadocType")
public class BaseController {
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> isAlive(Map<String, Object> model) {
        return Collections
                .singletonMap("today_is", valueOf(LocalDateTime.now()));
        
    }
    
}
