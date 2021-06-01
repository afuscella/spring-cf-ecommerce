package com.ecommerce.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.models.dto.CategoryDTO;
import com.ecommerce.backend.models.response.CategoryResponse;
import com.ecommerce.backend.repositories.CategoryRepository;

@Service
public class CategoryServices {

	@Autowired
	CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public CategoryResponse handleIndexAll() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDTO> response = categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
		return new CategoryResponse(response);

	}

}
