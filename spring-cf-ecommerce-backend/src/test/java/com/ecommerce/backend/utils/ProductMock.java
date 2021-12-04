package com.ecommerce.backend.utils;

import java.math.BigDecimal;
import java.util.UUID;

import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.models.dto.ProductDTO;

public class ProductMock {

	public static Product create() {
		Product product = new Product();
		product.setUuid(UUID.randomUUID());
		product.setName("Product Test");
		product.setDescription("Product Test Description");
		product.setPrice(new BigDecimal("10.00"));
		product.setImgUrl("https://via.placeholder.com/100.png");
		product.getCategories().add(CategoryMock.create());
		return product;
	}

	public static ProductDTO createDTO() {
		Product product = create();
		return new ProductDTO(product, product.getCategories());
	}

}
