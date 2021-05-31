package com.ecommerce.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.services.CategoryServices;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	@Autowired
	CategoryServices categoryServices;

	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> categories = categoryServices.handleIndexAll();
		return ResponseEntity.ok().body(categories);
	}

}
