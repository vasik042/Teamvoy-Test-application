package com.WebStore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItem {
    public CartItem(Product product, int quantity) {
        if (product.getQuantity() >= quantity) {
            this.quantity = quantity;
        } else {
            this.quantity = product.getQuantity();
        }
        this.product = product;
    }


    @Id
    @GeneratedValue
    private int id;
    private int quantity;
    @ManyToOne
    private Product product;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
