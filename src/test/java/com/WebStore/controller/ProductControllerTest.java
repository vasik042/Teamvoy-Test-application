package com.WebStore.controller;

import com.WebStore.entities.Product;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
	@Autowired
	private MockMvc mvc;
	@MockBean
	private ProductService productService;
	@Autowired
	ObjectMapper mapper;


	Product product1 = new Product(1, "Apple iPhone 11 64GB Black", 30000, 2);
	Product product2 = new Product(2, "Apple iPhone 14 128GB Midnight", 40000, 5);
	Product product3 = new Product(3, "Apple iPhone 13 mini 256GB Midnight", 20000, 10);


	@Test
	void getProductSuccess() throws Exception {
		Mockito.when(productService.getProduct(1)).thenReturn(product1);

		mvc.perform(MockMvcRequestBuilders
						.get("/products/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Apple iPhone 11 64GB Black")));
	}

	@Test
	void getAllProductSuccess() throws Exception {
		List<Product> products = new ArrayList<Product>(Arrays.asList(product1, product2, product3));
		Mockito.when(productService.getAllProductSorted("name")).thenReturn(products);

		mvc.perform(MockMvcRequestBuilders
						.get("/products/all?sortBy=name")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[2].quantity", is(10)));
	}

	@Test
	void createProductSuccess() throws Exception {
		Mockito.when(productService.createProduct(product1)).thenReturn(product1);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/products/manage/")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(product1));

		mvc.perform(mockRequest)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is("Apple iPhone 11 64GB Black")));
	}


	@Test
	void updateProductSuccess() throws Exception {
		Mockito.when(productService.updateProduct(1, product1)).thenReturn(product1);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.put("/products/manage/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(product1));

		mvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Apple iPhone 11 64GB Black")));
	}

	@Test
	void deleteProductSuccess() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.delete("/products/manage/1");

		mvc.perform(mockRequest)
				.andExpect(status().isNoContent());
	}
}
