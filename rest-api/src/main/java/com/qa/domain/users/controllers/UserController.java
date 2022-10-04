package com.qa.domain.users.controllers;

import com.qa.auth.jwt.payload.ApiResponse;
import com.qa.domain.users.exceptions.ResourceNotFoundException;
import com.qa.domain.users.models.User;
import com.qa.domain.users.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@SuppressWarnings("JavadocType")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> list() {
        return userService.getAll();
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> signUp(@RequestBody @Valid User user) {

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();
        
        userService.createUser(user);
        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "User registered successfully."));
    }
    
    @GetMapping(value = "/{id}")
    public Optional<User> getOne(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return userService.getOne(id);
    }

    @PutMapping(value = "/{id}")
    public Optional<User> updateUser(@PathVariable("id") Long id,
                           User user, @RequestBody @Valid User userFromDb) throws ResourceNotFoundException {
        BeanUtils.copyProperties(user, userFromDb);
        return userService.updateUser(id, userFromDb);
    }
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
    }
}
