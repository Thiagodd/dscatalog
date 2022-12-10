package com.thiagodd.dscatalog.domain.repository;

import com.thiagodd.dscatalog.domain.factory.ProductFactory;
import com.thiagodd.dscatalog.domain.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private Long existingId;
    private Long notExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        notExistingId = -1L;
        countTotalProducts = 25L;
    }

    @Test
    public void saveShouldPersustWithAutoincrementWhenIsNull() {
        Product product = ProductFactory.createProduct();
        product.setId(null);
        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingId);
        Optional<Product> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldEmptyResultDataAccessExceptionWhenIdNotExists() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(notExistingId);
        });
    }

    @Test
    public void findByIdShouldReturnOptionalNotEmptyWhenIdExists() {
        Optional<Product> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnOptionalEmptyWhenIdExists() {
        Optional<Product> result = repository.findById(notExistingId);
        Assertions.assertFalse(result.isPresent());
    }
}
