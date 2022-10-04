package com.qa.domain.data.users.roles;

import com.qa.domain.roles.repos.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RolesSampleData {
    
    private RoleRepository roleRepository;
    
    public void createSampleData() {
        roleRepository.save(RolesObjectMother.createUserRole());
        roleRepository.save(RolesObjectMother.createAdminRole());
    }
    
}
