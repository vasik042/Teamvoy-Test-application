package com.WebStore.controller;

import com.WebStore.entities.Order;
import com.WebStore.entities.Product;
import com.WebStore.service.OrderService;
import com.WebStore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping
    public Order getCart(HttpSession session) {
        Order order = getOrderFromSession(session);
        return order;
    }


    @PostMapping(value = "/{id}")
    public Order addProduct(@PathVariable int id, @RequestParam(required = false) Integer quantity, HttpSession session) {
        Product product = productService.getProduct(id);
        Order order = getOrderFromSession(session);

        if (quantity == null || quantity <= 0) {
            quantity = 1;
        }
        order.add(product, quantity);

        return order;
    }

    @PostMapping(value = "/buy")
    public ResponseEntity<String> payOrder(HttpSession session) {
        Order order = getOrderFromSession(session);
        if (order.getItems() == null) {
            return ResponseEntity.badRequest().body("Your cart is empty");
        }
        session.removeAttribute("Order");

        orderService.createOrder(order);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/clear")
    public ResponseEntity<String> clearOrder(HttpSession session) {
        Order order = getOrderFromSession(session);
        order.clear();

        return ResponseEntity.ok().build();
    }

    //ADMIN methods
    @GetMapping(value = "/manage/all")
    public List<Order> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping(value = "/manage/{id}")
    public Order getOrder(@PathVariable int id) {
        return orderService.getOrder(id);
    }


    private Order getOrderFromSession(HttpSession session) {
        Order order = (Order) session.getAttribute("Order");
        if (order == null) {
            order = new Order();
            session.setAttribute("Order", order);
        }
        return order;
    }

}
