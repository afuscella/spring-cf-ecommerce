package com.ecommerce.its.utils;

import com.ecommerce.backend.entities.Category;

import java.util.UUID;

public class CategoryMock {

	public static Category create() {
		Category category = new Category();
		category.setUuid(UUID.fromString("8e0748a4-7c61-48aa-91dd-e358da0fb81a"));
		category.setName("Electronics");
		return category;
	}

}
