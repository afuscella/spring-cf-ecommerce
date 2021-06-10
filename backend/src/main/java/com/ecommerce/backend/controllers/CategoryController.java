package com.ecommerce.backend.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	private CategoryServices categoryServices;

	@GetMapping
	public ResponseEntity<CategoryResponse> findAll(Pageable pageable) {
		CategoryResponse response = categoryServices.handleAllPaged(pageable);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/{uuid}")
	public ResponseEntity<CategoryDTO> findByIndex(
			@PathVariable
					UUID uuid) {
		CategoryDTO categoryDTO = categoryServices.handleIndex(uuid);
		return ResponseEntity.ok().body(categoryDTO);
	}

	@PostMapping
	public ResponseEntity<CategoryDTO> create(
			@RequestBody
					CategoryDTO categoryDTO) {
		categoryDTO = categoryServices.handleCreate(categoryDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(categoryDTO.getUuid()).toUri();
		return ResponseEntity.created(uri).body(categoryDTO);
	}

	@PutMapping(value = "/{uuid}")
	public ResponseEntity<CategoryDTO> updateByIndex(
			@PathVariable
					UUID uuid,
			@RequestBody
					CategoryDTO categoryDTO) {
		categoryDTO = categoryServices.handleUpdateByIndex(uuid, categoryDTO);
		return ResponseEntity.ok().body(categoryDTO);
	}

	@DeleteMapping(value = "/{uuid}")
	public ResponseEntity<Void> deleteByUuid(
			@PathVariable
					UUID uuid) {
		categoryServices.handleDeleteByIndex(uuid);
		return ResponseEntity.noContent().build();
	}

}
