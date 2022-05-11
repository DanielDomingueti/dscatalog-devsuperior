package com.domingueti.dscatalog.tests;

import java.time.Instant;
import java.util.HashSet;

import com.domingueti.dscatalog.dto.ProductDTO;
import com.domingueti.dscatalog.entities.Category;
import com.domingueti.dscatalog.entities.Product;

public class Factory {

	public static Product createProduct() {
		Product product = new Product(1L, "name", "desc", 100.0, "string.img", Instant.now(), new HashSet<>());
		product.getCategories().add(createCategory());
		return product;
	}

	public static ProductDTO createProductDTO() {
		Product prod = createProduct();
		return new ProductDTO(prod, prod.getCategories());
	}

	public static Category createCategory() {
		return new Category(1L, "Electronics");
	}
	
}
