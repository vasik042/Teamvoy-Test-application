package com.WebStore.repository;

import com.WebStore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update Product p set p.name = ?1, p.price = ?2, p.quantity = ?3 where p.id = ?4")
    void updateProduct(String name, float price, int quantity, int id);
}
