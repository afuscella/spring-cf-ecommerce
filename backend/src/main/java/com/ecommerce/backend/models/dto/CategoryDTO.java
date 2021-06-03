package com.ecommerce.backend.models.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import com.ecommerce.backend.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO implements Serializable {

	private UUID uuid;

	private String name;

	private Instant createdAt;

	private Instant updatedAt;

	public CategoryDTO(Category category) {
		this.uuid = category.getUuid();
		this.name = category.getName();
		this.createdAt = category.getCreatedAt();
		this.updatedAt = category.getUpdatedAt();
	}

}
