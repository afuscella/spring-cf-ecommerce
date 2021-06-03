package com.ecommerce.backend.services;

import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.exceptions.DatabaseIntegrityException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.models.dto.CategoryDTO;
import com.ecommerce.backend.models.response.CategoryResponse;
import com.ecommerce.backend.repositories.CategoryRepository;

@Service
public class CategoryServices {

	@Autowired
	CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public CategoryResponse handleAllPaged(PageRequest pageRequest) {
		Page<Category> categories = categoryRepository.findAll(pageRequest);

		Page<CategoryDTO> data = categories.map(CategoryDTO::new);
		return new CategoryResponse(data);
	}

	@Transactional(readOnly = true)
	public CategoryDTO handleIndex(UUID uuid) {
		Optional<Category> categoryOptional = categoryRepository.findById(uuid);
		Category entity = categoryOptional.orElseThrow(ResourceNotFoundException::new);

		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO handleInsert(CategoryDTO categoryDTO) {
		Category entity = new Category();
		entity.setName(categoryDTO.getName());
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO handleUpdateByUuid(UUID uuid, CategoryDTO categoryDTO) {
		try {
			Category entity = categoryRepository.getOne(uuid);
			entity.setName(categoryDTO.getName());
			entity = categoryRepository.save(entity);
			return new CategoryDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException();
		}
	}

	public void handleDeleteByUuid(UUID uuid) {
		try {
			categoryRepository.deleteById(uuid);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException();
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseIntegrityException();
		}
	}

}
