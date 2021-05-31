package com.ecommerce.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.repositories.CategoryRepository;

@Service
public class CategoryServices {

	@Autowired
	CategoryRepository categoryRepository;

	public List<Category> handleIndexAll() {
		return categoryRepository.findAll();
	}

}
