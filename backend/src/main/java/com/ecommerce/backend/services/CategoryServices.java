package com.ecommerce.backend.services;

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
	public CategoryDTO handleIndex(UUID uuid) {
		Optional<Category> categoryOptional = categoryRepository.findById(uuid);
		Category categoryEntity = categoryOptional.orElseThrow(EntityNotFoundException::new);

		return new CategoryDTO(categoryEntity);
	}

	@Transactional
	public CategoryDTO handleInsert(CategoryDTO categoryDTO) {
		Category categoryEntity = new Category();
		categoryEntity.setName(categoryDTO.getName());
		Category entity = categoryRepository.save(categoryEntity);
		return new CategoryDTO(entity);
	}

}
