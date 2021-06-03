package com.ecommerce.backend.models.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO implements Serializable {

	private UUID uuid;

	private String name;

	private String description;

	private BigDecimal price;

	private String imgUrl;

	private List<CategoryDTO> categories = new ArrayList<>();

	private Instant createdAt;

	private Instant updatedAt;

	public ProductDTO(Product product) {
		this.uuid = product.getUuid();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.imgUrl = product.getImgUrl();
		this.createdAt = product.getCreatedAt();
		this.updatedAt = product.getUpdatedAt();
	}

	public ProductDTO(Product product, Set<Category> categories) {
		this(product);
		categories.forEach(category -> this.categories.add(new CategoryDTO(category)));
	}

}
