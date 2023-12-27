package com.dvFabricio.dscatalog.tests;

import java.time.Instant; 

import com.dvFabricio.dscatalog.dto.ProductDTO;
import com.dvFabricio.dscatalog.entities.Category;
import com.dvFabricio.dscatalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.pmg", Instant.parse("2022-03-16T03:00:00Z"));
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static Category createCategory() {
		return new Category(1L, "Electronics");
	}
}
