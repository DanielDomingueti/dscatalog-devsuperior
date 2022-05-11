package com.domingueti.dscatalog.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.domingueti.dscatalog.entities.Product;
import com.domingueti.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	private long existingId;
	private long nonExistingId;
	private Product product;

	@Autowired
	private ProductRepository repository;

	@BeforeEach
	void setup() {
		existingId = 1L;
		nonExistingId = 0L;
		product = Factory.createProduct();

	}

	@Test
	public void findByIdShouldReturnProductOptWhenIdExists() {

		Optional<Product> obj = repository.findById(existingId);

		assertTrue(obj.isPresent());

	}

	@Test
	public void findByIdShouldReturnNullWhenIdDoesNotExist() {

		Optional<Product> obj = repository.findById(nonExistingId);

		assertFalse(obj.isPresent());

	}

	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		product.setId(null);
		product = repository.save(product);

		assertNotNull(product.getId());
	}

	@Test
	public void deleteShouldDeleteWhenIdExists() {

		repository.deleteById(1L);

		Optional<Product> result = repository.findById(existingId);

		assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

		assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}

}
