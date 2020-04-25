package com.qa.domain.data;

import com.qa.domain.data.products.ProductsSampleData;
import com.qa.domain.data.users.roles.RolesSampleData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class DataInit implements ApplicationRunner {

    private ProductsSampleData productsSampleData;
    private RolesSampleData rolesSampleData;
    
    public void run(ApplicationArguments args) {
        
        log.info("Loading sample PRODUCTS DATA into DB...");
        productsSampleData.createSampleData();

        log.info("Loading sample ROLES DATA into DB...");
        rolesSampleData.createSampleData();
    }
    
}
