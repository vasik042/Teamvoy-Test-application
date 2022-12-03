package com.WebStore.controller;

import com.WebStore.entities.Order;
import com.WebStore.entities.Product;
import com.WebStore.service.OrderService;
import com.WebStore.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private OrderService orderService;
    @Autowired
    ObjectMapper mapper;

    Product product1 = new Product(1, "Apple iPhone 11 64GB Black", 30000, 2);
    Product product2 = new Product(2, "Apple iPhone 14 128GB Midnight", 40000, 5);
    Product product3 = new Product(3, "Apple iPhone 13 mini 256GB Midnight", 20000, 10);


    @Test
    void getCartSuccess() throws Exception {
        Order order = new Order();
        order.add(product1, 1);

        mvc.perform(MockMvcRequestBuilders
                        .get("/orders")
                        .sessionAttr("Order", order)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].quantity", is(1)))
                .andExpect(jsonPath("$.items[0].product.id", is(1)));
    }

    @Test
    void addProductSuccess() throws Exception {
        Mockito.when(productService.getProduct(1)).thenReturn(product1);

        mvc.perform(MockMvcRequestBuilders
                        .post("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].quantity", is(1)))
                .andExpect(jsonPath("$.items[0].product.id", is(1)));
    }

    @Test
    void addSeveralProductSuccess() throws Exception {
        Mockito.when(productService.getProduct(2)).thenReturn(product2);

        mvc.perform(MockMvcRequestBuilders
                        .post("/orders/2?quantity=8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].quantity", is(5)))
                .andExpect(jsonPath("$.items[0].product.id", is(2)));
    }

    @Test
    void addNegativeNumberProductSuccess() throws Exception {
        Mockito.when(productService.getProduct(3)).thenReturn(product3);

        mvc.perform(MockMvcRequestBuilders.post("/orders/3?quantity=-10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].quantity", is(1)))
                .andExpect(jsonPath("$.items[0].product.id", is(3)));
    }

    @Test
    void payOrderSuccess() throws Exception {
        Order order = new Order();
        order.add(product1, 1);

        Mockito.when(orderService.createOrder(order)).thenReturn(order);

        mvc.perform(MockMvcRequestBuilders
                        .post("/orders/buy")
                        .sessionAttr("Order", order))
                .andExpect(status().isOk());
    }

    @Test
    void payOrderBadRequest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/orders/pay");

        mvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    void clearSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/orders/clear"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllOrders() throws Exception {
        Order order = new Order();
        order.setId(1);

        Mockito.when(orderService.getAll()).thenReturn(Arrays.asList(order));

        mvc.perform(MockMvcRequestBuilders.get("/orders/manage/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void getOrder() throws Exception {
        Order order = new Order();
        order.setId(1);

        Mockito.when(orderService.getOrder(1)).thenReturn(order);

        mvc.perform(MockMvcRequestBuilders.get("/orders/manage/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }
}
