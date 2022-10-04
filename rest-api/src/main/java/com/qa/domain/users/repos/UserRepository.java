package com.qa.domain.users.repos;

import com.qa.domain.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("JavadocType")
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    List<User> findByIdIn(List<Long> userIds);
    
}
