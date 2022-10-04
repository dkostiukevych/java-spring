package com.qa.domain.data.users.roles;

import com.qa.domain.roles.models.Role;

import static com.qa.domain.roles.models.RoleName.ROLE_ADMIN;
import static com.qa.domain.roles.models.RoleName.ROLE_USER;

public abstract class RolesObjectMother {
    
    public static Role createUserRole() {
        return Role.builder()
                .name(ROLE_USER)
                .build();
    }

    public static Role createAdminRole() {
        return Role.builder()
                .name(ROLE_ADMIN)
                .build();
    }
    
}
