package com.ecommerce.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.exceptions.DatabaseIntegrityException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.models.dto.ProductDTO;
import com.ecommerce.backend.models.dto.TransformProductDTO;
import com.ecommerce.backend.models.response.ProductResponse;
import com.ecommerce.backend.repositories.ProductRepository;
import com.ecommerce.backend.utils.ProductMock;

@ExtendWith(SpringExtension.class)
public class ProductServicesTests {

	private final UUID TEST_UUID = UUID.randomUUID();

	@Mock
	ProductRepository productRepository;

	@Mock
	TransformProductDTO transformProductDTO;

	@InjectMocks
	ProductServices productServices;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void handleAllPagedShouldRetrievePage() {
		/*
		PageImpl<Product> page = new PageImpl<>(List.of(ProductMock.create()));
		Mockito.when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

		Pageable pageable = PageRequest.of(0, 10);
		ProductResponse response = productServices.findAllPaged(pageable);

		Assertions.assertNotNull(response);
		Mockito.verify(productRepository, Mockito.atLeastOnce()).findAll(pageable);
		*/
	}

	@Test
	public void handleIndexShouldRetrieveObjectWhenUuidExists() {
		/*
		Optional<Product> productOptional = Optional.of(ProductMock.create());
		Mockito.when(productRepository.findById(TEST_UUID)).thenReturn(productOptional);

		ProductDTO productDTO = productServices.index(TEST_UUID);

		Assertions.assertNotNull(productDTO);
		Mockito.verify(productRepository, Mockito.atLeastOnce()).findById(TEST_UUID);
		*/
	}

	@Test
	public void handleIndexShouldThrowResourceNotFoundExceptionWhenUuidDoesNotExist() {
		Mockito.when(productRepository.findById(TEST_UUID)).thenReturn(Optional.empty());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productServices.index(TEST_UUID);
		});
		Mockito.verify(productRepository, Mockito.atLeastOnce()).findById(TEST_UUID);
	}

	@Test
	public void deleteShouldDeleteObjectWhenUuidExists() {
		Mockito.doNothing().when(productRepository).deleteById(TEST_UUID);
		Assertions.assertDoesNotThrow(() -> {
			productServices.deleteByIndex(TEST_UUID);
		});
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(TEST_UUID);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenUuidDoesNotExist() {
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(TEST_UUID);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productServices.deleteByIndex(TEST_UUID);
		});
		Mockito.verify(productRepository, Mockito.atLeastOnce()).deleteById(TEST_UUID);
	}

	@Test
	public void deleteShouldThrowDataIntegrityViolationExceptionWhenEntryIntegrityWasViolated() {
		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(TEST_UUID);
		Assertions.assertThrows(DatabaseIntegrityException.class, () -> {
			productServices.deleteByIndex(TEST_UUID);
		});
		Mockito.verify(productRepository, Mockito.atLeastOnce()).deleteById(TEST_UUID);
	}

}
