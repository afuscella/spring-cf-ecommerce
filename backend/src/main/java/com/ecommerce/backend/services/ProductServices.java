package com.ecommerce.backend.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.models.dto.ProductDTO;
import com.ecommerce.backend.models.dto.TransformProductDTO;
import com.ecommerce.backend.models.response.ProductResponse;
import com.ecommerce.backend.repositories.ProductRepository;

@Service
public class ProductServices {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	TransformProductDTO transformProductDTO;

	@Transactional(readOnly = true)
	public ProductResponse handleAllPaged(PageRequest pageRequest) {
		Page<Product> products = productRepository.findAll(pageRequest);

		Page<ProductDTO> data = products.map(product -> new ProductDTO(product, product.getCategories()));
		return new ProductResponse(data);
	}

	@Transactional(readOnly = true)
	public ProductDTO handleIndex(UUID uuid) {
		Optional<Product> productOptional = productRepository.findById(uuid);
		Product entity = productOptional.orElseThrow(ResourceNotFoundException::new);

		return new ProductDTO(entity, entity.getCategories());
	}

	//	@Transactional
	//	public ProductDTO handleInsert(ProductDTO productDTO) {
	//		Product entity = transformProductDTO.toEntity(productDTO);
	//		entity = productRepository.save(entity);
	//		return new ProductDTO(entity);
	//	}
	//
	//	@Transactional
	//	public ProductDTO handleUpdateByUuid(UUID uuid, ProductDTO productDTO) {
	//		try {
	//			Product entity = productRepository.getOne(uuid);
	//			// entity.setName(productDTO.getName());
	//			// entity = TransformProductDTO.toEntity(productDTO);
	//			entity = productRepository.save(entity);
	//			return new ProductDTO(entity);
	//		}
	//		catch (EntityNotFoundException e) {
	//			throw new ResourceNotFoundException();
	//		}
	//	}

	//	public void handleDeleteByUuid(UUID uuid) {
	//		try {
	//			productRepository.deleteById(uuid);
	//		}
	//		catch (EmptyResultDataAccessException e) {
	//			throw new ResourceNotFoundException();
	//		}
	//		catch (DataIntegrityViolationException e) {
	//			throw new DatabaseIntegrityException();
	//		}
	//	}

}
