package com.ecommerce.backend.models.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.repositories.CategoryRepository;

@Component
public class TransformProductDTO {

	@Autowired
	private CategoryRepository categoryRepository;

	public Product toEntity(ProductDTO productDTO) {
		Product product = new Product();
		return mapProduct(productDTO, product);
	}

	public Product toEntity(ProductDTO productDTO, Product entityRef) {
		return mapProduct(productDTO, entityRef);
	}

	private Product mapProduct(ProductDTO productDTO, Product product) {
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setImgUrl(productDTO.getImgUrl());

		product.getCategories().clear();
		for (CategoryDTO categoryDTO : productDTO.getCategories()) {
			Category category = categoryRepository.getOne(categoryDTO.getUuid());
			product.getCategories().add(category);
		}
		return product;
	}

}
