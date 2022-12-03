package com.WebStore.service;

import com.WebStore.entities.Product;
import com.WebStore.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepo productRepo;
    @InjectMocks
    private ProductService productService;

    Product product1 = new Product(1, "Apple iPhone 11 64GB Black", 30000, 2);
    Product product2 = new Product(2, "Apple iPhone 14 128GB Midnight", 40000, 5);
    Product product3 = new Product(3, "Apple iPhone 13 mini 256GB Midnight", 20000, 10);

    @Test
    void getProductSuccess() {
        Mockito.when(productRepo.findById(1)).thenReturn(Optional.of(product1));

        assertEquals(productService.getProduct(1), product1);
    }

    @Test
    void getAllProductsSuccess() {
        Mockito.when(productRepo.findAll()).thenReturn(Arrays.asList(product1, product2, product3));

        List<Product> all = productService.getAllProductSorted(null);
        assertEquals(all.get(0), product1);
        assertEquals(all.get(1), product2);
        assertEquals(all.get(2), product3);
    }

    @Test
    void getAllProductsSortedSuccess() {
        Mockito.when(productRepo.findAll(Sort.by(Sort.Order.asc("name")))).thenReturn(Arrays.asList(product3, product1, product2));

        List<Product> all = productService.getAllProductSorted("name");
        assertEquals(all.get(0), product3);
        assertEquals(all.get(1), product1);
        assertEquals(all.get(2), product2);
    }

}