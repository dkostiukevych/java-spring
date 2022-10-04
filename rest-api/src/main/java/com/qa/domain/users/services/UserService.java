package com.qa.domain.users.services;

import com.qa.domain.users.exceptions.ResourceNotFoundException;
import com.qa.domain.users.models.User;
import com.qa.domain.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("javadocType")
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<User> getOne(Long id) throws ResourceNotFoundException {
        final Optional<User> user = userRepository.findById(id);
        catchAndThrowException(user, id);
        return user;
    }

    @Transactional
    public List<User> getAll() {
        return userRepository.findAll();
    }
    
    @Transactional
    public Object createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreationDate(LocalDateTime.now());
        userRepository.save(user);
        return "User registered successfully.";
    }
    
    @Transactional
    public Optional<User> updateUser(Long id, User userFromDb) throws ResourceNotFoundException {
        final Optional<User> user = userRepository.findById(id);
        catchAndThrowException(user, id);
        userFromDb.setPassword(passwordEncoder.encode(userFromDb.getPassword()));
        userRepository.save(userFromDb);
        return user;
    }
    
    @Transactional
    public void deleteUser(Long id) throws ResourceNotFoundException {
        userRepository.deleteById(id);
    }

    private void catchAndThrowException(Optional<User> user, Long id) throws ResourceNotFoundException {
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User", "id", id.toString());
        }
    }
}
