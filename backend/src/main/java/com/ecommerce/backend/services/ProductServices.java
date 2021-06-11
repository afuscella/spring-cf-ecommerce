package com.ecommerce.backend.services;

import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.exceptions.DatabaseIntegrityException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.models.dto.ProductDTO;
import com.ecommerce.backend.models.dto.TransformProductDTO;
import com.ecommerce.backend.models.response.ProductResponse;
import com.ecommerce.backend.repositories.ProductRepository;

@Service
public class ProductServices {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TransformProductDTO transformProductDTO;

	@Transactional(readOnly = true)
	public ProductResponse allPaged(Pageable pageable) {
		Page<Product> products = productRepository.findAll(pageable);

		Page<ProductDTO> data = products.map(product -> new ProductDTO(product, product.getCategories()));
		return new ProductResponse(data);
	}

	@Transactional(readOnly = true)
	public ProductDTO index(UUID uuid) {
		Optional<Product> productOptional = productRepository.findById(uuid);
		Product entity = productOptional.orElseThrow(ResourceNotFoundException::new);

		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO create(ProductDTO productDTO) {
		Product entity = transformProductDTO.toEntity(productDTO);
		entity = productRepository.save(entity);
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO updateByIndex(UUID uuid, ProductDTO productDTO) {
		try {
			Product entity = productRepository.getOne(uuid);
			entity = transformProductDTO.toEntity(productDTO, entity);

			entity = productRepository.save(entity);
			return new ProductDTO(entity, entity.getCategories());
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException();
		}
	}

	public void deleteByIndex(UUID uuid) {
		try {
			productRepository.deleteById(uuid);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException();
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseIntegrityException();
		}
	}

}
