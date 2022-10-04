package com.qa.domain.products.service;

import com.qa.domain.products.models.Product;
import com.qa.exceptions.EntityNotFoundException;
import com.qa.domain.products.repos.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@SuppressWarnings("javadocType")
public class ProductService {

    @Autowired
    private ProductsRepository productsRepository;

    @Transactional
    public Iterable<Product> getAllItems() {
        return productsRepository.findAll();
    }

    @Transactional
    public Optional<Product> getOne(Long id) throws EntityNotFoundException {
        final Optional<Product> product = productsRepository.findById(id);
        catchAndThrowException(id, product);
        return product;
    }

    @Transactional
    public Product updateProduct(Long id, Product productFromDb) throws EntityNotFoundException {
        final Optional<Product> product = productsRepository.findById(id);
        catchAndThrowException(id, product);
        return productsRepository.save(productFromDb);
    }

    @Transactional
    public Product createProduct(Product product) {
        return productsRepository.save(product);
    }

    @Transactional
    public void delete(Long id) throws EntityNotFoundException {
        final Optional<Product> product = productsRepository.findById(id);
        catchAndThrowException(id, product);
        productsRepository.deleteById(id);
    }

    private void catchAndThrowException(Long id, Optional<Product> product) throws EntityNotFoundException {
        if (!product.isPresent()) {
            throw new EntityNotFoundException(Product.class, "id", id.toString());
        }
    }

}
