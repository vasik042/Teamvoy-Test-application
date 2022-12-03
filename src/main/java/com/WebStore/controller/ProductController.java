package com.WebStore.controller;

import com.WebStore.entities.Product;
import com.WebStore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public Product getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String sortBy) {
        return new ResponseEntity(productService.getAllProductSorted(sortBy), HttpStatus.OK);
    }


    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity handleMissingParams(PropertyReferenceException ex) {
        return ResponseEntity.badRequest().body(String.format("URL parameter ':%s' not valid. " +
                "URL parameter must be: 'name', 'price' or 'quantity'", ex.getPropertyName()));
    }

    //ADMIN methods
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/manage/")
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping(value = "/manage/{id}")
    public Product updateProduct(@PathVariable int id, @Valid @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping(value = "/manage/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
