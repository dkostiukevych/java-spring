package com.qa.domain.products.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.qa.domain.products.models.Product;
import com.qa.domain.products.service.ProductService;
import com.qa.domain.products.views.Views;
import com.qa.exceptions.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(name = "Products controller",value = "api/products")
@SuppressWarnings("JavadocType")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @JsonView(Views.IdName.class)
    public Iterable<Product> list() {
        return productService.getAllItems();
    }

    @GetMapping(name = "GET one", value = "/{id}")
    @JsonView(Views.FullMessage.class)
    public Optional<Product> getOne(@PathVariable("id") Long id) throws EntityNotFoundException {
       return productService.getOne(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @JsonView(Views.FullMessage.class)
    public Product create(@RequestBody @Valid Product product)  {
        product.setCreationDate(LocalDateTime.now());
        return productService.createProduct(product);
    }
    
    @PutMapping(name = "PUT one",value = "/{id}")
    @JsonView(Views.FullMessage.class)
    public Product update(@PathVariable("id") Long id, 
                          Product productFromDb,
                          @RequestBody @Valid Product product) throws EntityNotFoundException {
        BeanUtils.copyProperties(product, productFromDb, "id");
        return productService.updateProduct(id, productFromDb);
    }
    
    @DeleteMapping(name = "DELETE one",value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
        productService.delete(id);
    }
    
}
