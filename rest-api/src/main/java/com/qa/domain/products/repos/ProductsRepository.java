package com.qa.domain.products.repos;

import com.qa.domain.products.models.Product;
import org.springframework.data.repository.CrudRepository;

@SuppressWarnings("JavadocType")
public interface ProductsRepository extends CrudRepository<Product, Long> {
}

