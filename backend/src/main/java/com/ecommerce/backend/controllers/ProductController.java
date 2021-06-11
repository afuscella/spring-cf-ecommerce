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

import com.ecommerce.backend.models.dto.ProductDTO;
import com.ecommerce.backend.models.response.ProductResponse;
import com.ecommerce.backend.services.ProductServices;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductServices productServices;

	@GetMapping
	public ResponseEntity<ProductResponse> findAll(Pageable pageable) {
		ProductResponse response = productServices.allPaged(pageable);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/{uuid}")
	public ResponseEntity<ProductDTO> findByIndex(
			@PathVariable
					UUID uuid) {
		ProductDTO productDTO = productServices.index(uuid);
		return ResponseEntity.ok().body(productDTO);
	}

	@PostMapping
	public ResponseEntity<ProductDTO> create(
			@RequestBody
					ProductDTO productDTO) {
		productDTO = productServices.create(productDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(productDTO.getUuid()).toUri();
		return ResponseEntity.created(uri).body(productDTO);
	}

	@PutMapping(value = "/{uuid}")
	public ResponseEntity<ProductDTO> updateByIndex(
			@PathVariable
					UUID uuid,
			@RequestBody
					ProductDTO productDTO) {
		productDTO = productServices.updateByIndex(uuid, productDTO);
		return ResponseEntity.ok().body(productDTO);
	}

	@DeleteMapping(value = "/{uuid}")
	public ResponseEntity<Void> deleteByIndex(
			@PathVariable
					UUID uuid) {
		productServices.deleteByIndex(uuid);
		return ResponseEntity.noContent().build();
	}

}
