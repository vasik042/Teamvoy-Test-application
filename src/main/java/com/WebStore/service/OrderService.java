package com.WebStore.service;

import com.WebStore.entities.CartItem;
import com.WebStore.entities.Order;
import com.WebStore.repository.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    OrderRepo orderRepo;
    CartItemService cartItemService;
    ProductService productService;

    public Order getOrder(int id) {
        return orderRepo.findById(id).get();
    }

    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    public Order createOrder(Order order) {
        order.setDateOfOrdering(LocalDateTime.now());
        order.setOrderStatus("isPaid");
        order = orderRepo.save(order);

        for (CartItem i : order.getItems()) {
            i.setOrder(order);
            cartItemService.createItem(i);

            i.getProduct().setQuantity(i.getProduct().getQuantity() - i.getQuantity());
            productService.updateProduct(i.getProduct().getId(), i.getProduct());
        }

        return order;
    }
}
