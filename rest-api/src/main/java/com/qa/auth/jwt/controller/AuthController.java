package com.qa.auth.jwt.controller;

import com.qa.auth.jwt.component.JwtTokenProvider;
import com.qa.auth.jwt.model.JwtAuthenticationResponse;
import com.qa.auth.jwt.payload.ApiResponse;
import com.qa.domain.roles.models.Role;
import com.qa.domain.roles.models.RoleName;
import com.qa.domain.roles.repos.RoleRepository;
import com.qa.domain.users.models.LoginRequest;
import com.qa.domain.users.models.User;
import com.qa.domain.users.models.SignUpRequest;
import com.qa.domain.users.repos.UserRepository;
import com.qa.exceptions.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@SuppressWarnings("JavadocType")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/sign-in")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest user) {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/sign-up")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        
        // Creating user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreationDate(LocalDateTime.now());
        
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "User registered successfully."));
    }
    
}
