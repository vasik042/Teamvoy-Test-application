package com.WebStore.service;

import com.WebStore.entities.Product;
import com.WebStore.repository.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    ProductRepo productRepo;

    public Product getProduct(int id) {
        return productRepo.findById(id).get();
    }

    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    public Product updateProduct(int id, Product product) {
        productRepo.updateProduct(product.getName(),
                product.getPrice(),
                product.getQuantity(),
                id);
        return getProduct(id);
    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    public List<Product> getAllProductSorted(String sortBy) {
        if (sortBy == null) {
            return productRepo.findAll();
        }

        Sort sort = Sort.by(Sort.Order.asc(sortBy));
        return productRepo.findAll(sort);
    }
}
