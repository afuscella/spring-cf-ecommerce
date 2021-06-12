package com.ecommerce.backend.services;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.models.dto.ProductDTO;
import com.ecommerce.backend.models.response.ProductResponse;
import com.ecommerce.backend.repositories.ProductRepository;

@SpringBootTest
@Transactional
public class ProductServiceITests {

	private final UUID TEST_UUID = UUID.fromString("ba005e67-e457-48f3-8cf9-9e40c7e36a95");

	private final UUID TEST_UUID_INVALID = UUID.fromString("cd662ae5-640a-4605-8bba-45406c198903");

	@Autowired
	private ProductServices productServices;

	@Autowired
	private ProductRepository productRepository;

	@BeforeEach
	void setup() {

	}

	@Test
	public void findAllPagedShouldReturnPageWhenRequested() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		ProductResponse response = productServices.findAllPaged(pageRequest);

		Assertions.assertTrue(response.getData().hasNext());
		Assertions.assertEquals(0, response.getData().getNumber());
		Assertions.assertEquals(10, response.getData().getSize());
	}

	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {
		PageRequest pageRequest = PageRequest.of(100, 10);
		ProductResponse response = productServices.findAllPaged(pageRequest);

		Assertions.assertTrue(response.getData().isEmpty());
	}

	@Test
	public void findAllPagedShouldReturnOrderedPageWhenSortByName() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		ProductResponse response = productServices.findAllPaged(pageRequest);

		Page<ProductDTO> data = response.getData();

		Assertions.assertTrue(data.hasNext());
		Assertions.assertEquals(data.getContent().get(0).getName(), "Macbook Pro");
		Assertions.assertEquals(data.getContent().get(1).getName(), "PC Gamer");
		Assertions.assertEquals(data.getContent().get(2).getName(), "PC Gamer Alfa");
	}

	@Test
	public void deleteByIndexShouldDeleteResourceWhenUuidExists() {
		productServices.deleteByIndex(TEST_UUID);

		Optional<Product> product = productRepository.findById(TEST_UUID);
		Assertions.assertTrue(product.isEmpty());
	}

	@Test
	public void deleteByIndexShould() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productServices.deleteByIndex(TEST_UUID_INVALID);
		});
	}

}
