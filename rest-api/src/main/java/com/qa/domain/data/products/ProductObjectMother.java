package com.qa.domain.data.products;

import com.qa.domain.products.models.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class ProductObjectMother {
    
    private static List<String> urls = new ArrayList<String>() {{
        add("http://host.com");
    }};
    
    public static Product createProduct() {
        
            return Product.builder()
                    .creationDate(LocalDateTime.now())
                    .name("Atlantic canary.")
                    .description("Atlantic canary description.")
                    .price(100)
                    .imageUrls(urls)
                    .build();
    }
}
