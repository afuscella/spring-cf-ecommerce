package com.ecommerce.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.exceptions.EntityNotFoundException;
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
		List<CategoryDTO> data = categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
		return new CategoryResponse(data);
	}

	@Transactional(readOnly = true)
	public CategoryResponse handleIndex(UUID uuid) {
		Optional<Category> categoryEntity = categoryRepository.findById(uuid);
		Category category = categoryEntity.orElseThrow(EntityNotFoundException::new);

		List<CategoryDTO> data = new ArrayList<>();
		data.add(new CategoryDTO(category));

		return new CategoryResponse(data);
	}

}
