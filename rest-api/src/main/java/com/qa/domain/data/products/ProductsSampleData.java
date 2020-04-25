package com.qa.domain.data.products;

import com.qa.domain.products.repos.ProductsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProductsSampleData {

    private ProductsRepository productsRepository;

    public void createSampleData() {
        productsRepository.save(ProductObjectMother.createProduct());
    }
}

