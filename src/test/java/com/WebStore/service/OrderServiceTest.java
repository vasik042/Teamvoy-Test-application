package com.WebStore.service;

import com.WebStore.entities.Order;
import com.WebStore.repository.OrderRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepo orderRepo;
    @InjectMocks
    private OrderService orderService;

    Order order1 = new Order(1, new HashSet<>(), 10000, null, "none");
    Order order2 = new Order(2, new HashSet<>(), 20000, null, "none");

    @Test
    void getOrderSuccess() {
        Mockito.when(orderRepo.findById(1)).thenReturn(Optional.of(order1));

        assertEquals(orderService.getOrder(1), order1);
    }

    @Test
    void getAllOrdersSuccess() {
        Mockito.when(orderRepo.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> all = orderService.getAll();
        assertEquals(all.get(0), order1);
        assertEquals(all.get(1), order2);
    }

    @Test
    void createOrderSuccess() {
        Mockito.when(orderRepo.save(order1)).thenReturn(order1);

        Order created = orderService.createOrder(order1);
        assertNotNull(created.getDateOfOrdering());
        assertEquals(created.getOrderStatus(), "isPaid");
    }
}
