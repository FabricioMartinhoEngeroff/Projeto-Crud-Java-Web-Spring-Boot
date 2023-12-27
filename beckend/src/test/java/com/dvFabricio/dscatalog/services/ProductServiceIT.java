package com.dvFabricio.dscatalog.services;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.dvFabricio.dscatalog.dto.ProductDTO;
import com.dvFabricio.dscatalog.repositories.ProductRepository;
import com.dvFabricio.dscatalog.services.exceptions.ResourceNotFoundExceptions;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductRepository repository;

	private Long existingId;
	private Long nonExistingId;
	private Long counteTotalProduct;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		counteTotalProduct = 25L;
	}

	@Test
	public void deleteShouldDeleteResourcewhenIdExists() {

		service.delete(existingId);
		Assertions.assertEquals(counteTotalProduct - 1, repository.count());

	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionwhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundExceptions.class, () -> {
			service.delete(nonExistingId);
		});
	}

	@Test
	public void findAllShouldReturnPageWhenPage0Size10() {

		PageRequest pageRequest = PageRequest.of(0, 10);

		Page<ProductDTO> result = service.findAllPaged(0L, "", pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(counteTotalProduct, result.getTotalElements());

	}

	@Test
	public void findAllShouldReturnEmptyPageWhenPageDoesNotExist() {

		PageRequest pageRequest = PageRequest.of(50, 10);
		Page<ProductDTO> result = service.findAllPaged(0L, "", pageRequest);
		Assertions.assertTrue(result.isEmpty());

	}

	@Test
	public void findAllShouldReturnSortedPageWhenSortByName() {

		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

		Page<ProductDTO> result = service.findAllPaged(0L, "", pageRequest);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Boo", result.getContent().get(3).getName());

	}

}
