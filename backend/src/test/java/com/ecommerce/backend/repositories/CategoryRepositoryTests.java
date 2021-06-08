package com.ecommerce.backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.utils.CategoryMock;

@DataJpaTest
public class CategoryRepositoryTests {

	private final UUID TEST_UUID = UUID.fromString("7776802e-55c6-44d1-99c1-95045c9681be");

	@Autowired
	CategoryRepository categoryRepository;

	@Test
	public void findByIdShouldRetrieveObjectWhenIdExits() {
		Optional<Category> category = categoryRepository.findById(TEST_UUID);
		Assertions.assertTrue(category.isPresent());
	}

	@Test
	public void findByIdShouldRetrieveObjectEmptyWhenIdDoesNotExist() {
		UUID uuid = UUID.randomUUID();
		Optional<Category> category = categoryRepository.findById(uuid);
		Assertions.assertTrue(category.isEmpty());
	}

	@Test
	public void saveShouldPersistWhenObjectIsProvided() {
		Category category = categoryRepository.save(CategoryMock.create());
		Assertions.assertNotNull(category);
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		categoryRepository.deleteById(TEST_UUID);

		Optional<Category> category = categoryRepository.findById(TEST_UUID);
		Assertions.assertTrue(category.isEmpty());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
		UUID uuid = UUID.randomUUID();
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			categoryRepository.deleteById(uuid);
		});
	}

}
