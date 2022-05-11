package com.domingueti.dscatalog.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.domingueti.dscatalog.dto.ProductDTO;
import com.domingueti.dscatalog.entities.Category;
import com.domingueti.dscatalog.entities.Product;
import com.domingueti.dscatalog.repositories.CategoryRepository;
import com.domingueti.dscatalog.repositories.ProductRepository;
import com.domingueti.dscatalog.services.exceptions.DatabaseException;
import com.domingueti.dscatalog.services.exceptions.ResourceNotFoundException;
import com.domingueti.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	private long existingId;
	private long nonExistingId;
	private long violatedId;
	private PageImpl<Product> page;
	private Product product;
	private ProductDTO productDTO;
	private Category category;

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	@Mock
	private CategoryRepository categoryRepository;

	@BeforeEach
	void setup() {
		existingId = 1L;
		nonExistingId = 3L;
		violatedId = 2L;
		product = Factory.createProduct();
		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(Factory.createProduct()));
		category = Factory.createCategory();

		when(repository.findAll((Pageable) any())).thenReturn(page);

		when(repository.save(any())).thenReturn(product);

		when(repository.findById(existingId)).thenReturn(Optional.of(product));

		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		when(repository.getOne(existingId)).thenReturn(product);
		when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

		when(categoryRepository.getOne(existingId)).thenReturn(category);
		when(categoryRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		doNothing().when(repository).deleteById(existingId);
		doThrow(ResourceNotFoundException.class).when(repository).deleteById(nonExistingId);
		doThrow(DatabaseException.class).when(repository).deleteById(violatedId);

		doThrow(ResourceNotFoundException.class).when(repository).findById(nonExistingId);

	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, productDTO);
		});

	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {

		ProductDTO dto = service.update(existingId, productDTO);
		assertNotNull(dto);

	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {

		ProductDTO dto = service.findById(existingId);
		assertNotNull(dto);

		verify(repository).findById(existingId);
	}

	@Test
	public void shouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});

		verify(repository).findById(nonExistingId);
	}

	@Test
	public void findByIdShouldReturnOptProductWhenIdExists() {

		assertDoesNotThrow(() -> {
			service.findById(existingId);
		});

		verify(repository).findById(existingId);
	}

	@Test
	public void findAllPageableShouldReturnPageOfProductDTO() {

		Pageable pageable = PageRequest.of(0, 10);

		assertDoesNotThrow(() -> {
			service.findAllPaged(pageable);
		});

		verify(repository).findAll(pageable);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		verify(repository).deleteById(existingId);

	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

		verify(repository).deleteById(nonExistingId);

	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenIdIsViolated() {

		assertThrows(DatabaseException.class, () -> {
			service.delete(violatedId);
		});

		verify(repository).deleteById(violatedId);

	}

}
