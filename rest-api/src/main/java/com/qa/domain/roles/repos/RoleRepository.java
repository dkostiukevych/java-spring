package com.qa.domain.roles.repos;

import com.qa.domain.roles.models.Role;
import com.qa.domain.roles.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@SuppressWarnings("JavadocType")
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(RoleName roleName);
}
