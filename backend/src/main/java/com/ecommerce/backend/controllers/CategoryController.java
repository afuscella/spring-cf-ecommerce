package com.ecommerce.backend.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.backend.models.dto.CategoryDTO;
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
	public ResponseEntity<CategoryDTO> findByUuid(
			@PathVariable
					UUID uuid) {
		CategoryDTO categoryDTO = categoryServices.handleIndex(uuid);
		return ResponseEntity.ok().body(categoryDTO);
	}

	@PostMapping
	public ResponseEntity<CategoryDTO> create(
			@RequestBody
					CategoryDTO categoryDTO) {
		categoryDTO = categoryServices.handleInsert(categoryDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{uuid}")
				.buildAndExpand(categoryDTO.getUuid())
				.toUri();
		return ResponseEntity.created(uri).body(categoryDTO);
	}

}
