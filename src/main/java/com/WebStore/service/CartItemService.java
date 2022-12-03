package com.WebStore.service;

import com.WebStore.entities.CartItem;
import com.WebStore.repository.CartItemRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartItemService {
    CartItemRepo cartItemRepo;

    public CartItem createItem(CartItem cartItem) {
        return cartItemRepo.save(cartItem);
    }
}
