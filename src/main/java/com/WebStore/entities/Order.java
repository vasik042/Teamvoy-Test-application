package com.WebStore.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    @Id
    @GeneratedValue
    private int id;

    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "order")
    private Set<CartItem> items;
    private double totalPrice;
    private LocalDateTime dateOfOrdering;
    private String orderStatus;

    public void add(Product product, int quantity) {
        if (items == null) {
            items = new HashSet<>();
        }

        CartItem item = new CartItem(product, quantity);
        for (CartItem i : items) {
            if (i.getProduct().equals(product)) {
                if (i.getQuantity() + quantity >= product.getQuantity()) {
                    quantity = product.getQuantity() - i.getQuantity();
                }

                i.setQuantity(i.getQuantity() + quantity);
                totalPrice += product.getPrice() * quantity;
                return;
            }
        }

        items.add(item);
        totalPrice += product.getPrice() * item.getQuantity();
    }

    public void clear() {
        if(items != null){
            items.clear();
        }
        setTotalPrice(0);
    }
}
