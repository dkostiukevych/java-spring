package com.qa.domain.users.service;

import com.qa.domain.users.models.User;
import com.qa.domain.users.models.UserPrincipal;
import com.qa.domain.users.repos.UserRepository;
import com.qa.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@SuppressWarnings("JavadocType")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + username )
                );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) throws EntityNotFoundException {
        
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException( User.class, "id", id.toString())
        );

        return UserPrincipal.create(user);
    }
}
