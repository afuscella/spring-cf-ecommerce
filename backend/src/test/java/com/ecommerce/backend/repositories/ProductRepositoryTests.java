package com.ecommerce.backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.utils.ProductMock;

@DataJpaTest
public class ProductRepositoryTests {

	private final UUID TEST_UUID = UUID.fromString("ba005e67-e457-48f3-8cf9-9e40c7e36a95");

	@Autowired
	ProductRepository productRepository;

	@Test
	public void findByIdShouldRetrieveObjectWhenUuidExists() {
		Optional<Product> product = productRepository.findById(TEST_UUID);
		Assertions.assertTrue(product.isPresent());
	}

	@Test
	public void findByIdShouldRetrieveObjectNullWhenUuidDoesNotExists() {
		UUID uuid = UUID.randomUUID();
		Optional<Product> product = productRepository.findById(uuid);
		Assertions.assertTrue(product.isEmpty());
	}

	@Test
	public void saveShouldPersistWhenObjectUuidProvided() {
		Product product = productRepository.save(ProductMock.create());
		Assertions.assertNotNull(product.getUuid());
	}

	@Test
	public void deleteShouldDeleteObjectWhenUuidExists() {
		productRepository.deleteById(TEST_UUID);

		Optional<Product> product = productRepository.findById(TEST_UUID);
		Assertions.assertTrue(product.isEmpty());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenUuidDoesNotExist() {
		UUID uuid = UUID.randomUUID();
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			productRepository.deleteById(uuid);
		});
	}

}
