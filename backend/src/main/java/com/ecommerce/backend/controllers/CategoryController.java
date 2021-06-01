package com.ecommerce.backend.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.models.response.CategoryResponse;
import com.ecommerce.backend.services.CategoryServices;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	@Autowired
	CategoryServices categoryServices;

	@GetMapping
	public ResponseEntity<CategoryResponse> findAll() {
		CategoryResponse response = categoryServices.handleIndexAll();
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/{uuid}")
	public ResponseEntity<CategoryResponse> findByUuid(
			@PathVariable
					UUID uuid) {
		CategoryResponse response = categoryServices.handleIndex(uuid);
		return ResponseEntity.ok().body(response);
	}

}
