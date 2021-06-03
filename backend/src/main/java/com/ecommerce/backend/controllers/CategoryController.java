package com.ecommerce.backend.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<CategoryResponse> findAll(
			@RequestParam(value = "page",
					defaultValue = "0")
					Integer page,
			@RequestParam(value = "linesPerPage",
					defaultValue = "12")
					Integer linesPerPage,
			@RequestParam(value = "direction",
					defaultValue = "ASC")
					String direction,
			@RequestParam(value = "orderBy",
					defaultValue = "name")
					String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		CategoryResponse response = categoryServices.handleAllPaged(pageRequest);
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

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(categoryDTO.getUuid()).toUri();
		return ResponseEntity.created(uri).body(categoryDTO);
	}

	@PutMapping(value = "/{uuid}")
	public ResponseEntity<CategoryDTO> updateByUuid(
			@PathVariable
					UUID uuid,
			@RequestBody
					CategoryDTO categoryDTO) {
		categoryDTO = categoryServices.handleUpdateByUuid(uuid, categoryDTO);
		return ResponseEntity.ok().body(categoryDTO);
	}

	@DeleteMapping(value = "/{uuid}")
	public ResponseEntity<Void> deleteByUuid(
			@PathVariable
					UUID uuid) {
		categoryServices.handleDeleteByUuid(uuid);
		return ResponseEntity.noContent().build();
	}

}
