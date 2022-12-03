package com.WebStore;

import com.WebStore.entities.Product;
import com.WebStore.entities.User;
import com.WebStore.service.ProductService;
import com.WebStore.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WebStoreApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(WebStoreApplication.class, args);

		ProductService productService = (ProductService) run.getBean("productService");
		UserService userService = (UserService) run.getBean("userService");

		Product product1 = new Product();
		product1.setName("Apple iPhone 11 64GB Black");
		product1.setPrice(24499.00f);
		product1.setQuantity(38);

		Product product2 = new Product();
		product2.setName("Apple iPhone 14 128GB Midnight");
		product2.setPrice(43499.00f);
		product2.setQuantity(18);

		Product product3 = new Product();
		product3.setName("Apple iPhone 13 mini 256GB Midnight");
		product3.setPrice(34999.00f);
		product3.setQuantity(25);

		productService.createProduct(product1);
		productService.createProduct(product2);
		productService.createProduct(product3);

		User user = new User();
		user.setEmail("user@gmail.com");
		user.setPassword("user");
		user.setRole("ROLE_USER");

		User admin = new User();
		admin.setEmail("admin@gmail.com");
		admin.setPassword("admin");
		admin.setRole("ROLE_ADMIN");

		userService.createUser(user);
		userService.createUser(admin);
	}

}
