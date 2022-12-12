package com.thiagodd.dscatalog.domain.service;

import com.thiagodd.dscatalog.domain.model.Category;
import com.thiagodd.dscatalog.domain.model.Product;
import com.thiagodd.dscatalog.domain.model.dto.CategoryDto;
import com.thiagodd.dscatalog.domain.model.dto.ProductDto;
import com.thiagodd.dscatalog.domain.repository.CategoryRepository;
import com.thiagodd.dscatalog.domain.repository.ProductRepository;
import com.thiagodd.dscatalog.domain.service.exceptions.DatabaseIntegratyViolationException;
import com.thiagodd.dscatalog.domain.service.exceptions.ResourceNotFoundException;
import javax.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    String MSG_ENTITY_NOT_FOUND = "[CUSTOM] Produto com id %d não foi encontrada!";
    String MSG_ENTITY_IN_USE = "[CUSTOM] Produto com id %d não pode ser deletada, pois está em uso!";

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductDto::new);
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format(MSG_ENTITY_NOT_FOUND, id)
        ));
        return new ProductDto(product);
    }

    @Transactional
    public ProductDto insert(ProductDto productDto) {
        Product product = new Product();
        copyDtoToEntity(productDto, product);
        product = productRepository.save(product);
        return new ProductDto(product);
    }

    @Transactional
    public ProductDto update(Long id, ProductDto productDto) {
        try {
            Product product = productRepository.getById(id);
            copyDtoToEntity(productDto, product);
            product = productRepository.save(product);
            return new ProductDto(product);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        }
    }

    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseIntegratyViolationException(String.format(MSG_ENTITY_IN_USE, id));
        }
    }

    public void copyDtoToEntity(ProductDto dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setDate(dto.getDate());
        product.setImgUrl(dto.getImgUrl());
        product.setPrice(dto.getPrice());
        product.getCategories().clear();

        for (CategoryDto catDto : dto.getCategories()) {
            Category category = categoryRepository.getById(catDto.getId());
            product.getCategories().add(category);
        }
    }
}
