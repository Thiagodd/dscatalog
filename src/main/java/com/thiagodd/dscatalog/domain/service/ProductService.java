package com.thiagodd.dscatalog.domain.service;

import com.thiagodd.dscatalog.domain.model.Product;
import com.thiagodd.dscatalog.domain.model.dto.ProductDto;
import com.thiagodd.dscatalog.domain.repository.ProductRepository;
import com.thiagodd.dscatalog.domain.service.exceptions.DatabaseIntegratyViolationException;
import com.thiagodd.dscatalog.domain.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    String MSG_ENTITY_NOT_FOUND = "[CUSTOM] Produto com id %d não foi encontrada!";
    String MSG_ENTITY_IN_USE = "[CUSTOM] Produto com id %d não pode ser deletada, pois está em uso!";


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> findAllPaged(PageRequest pageRequest) {
        Page<Product> productPage = productRepository.findAll(pageRequest);
        return productPage.map(x -> new ProductDto(x, x.getCategories()));
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format(MSG_ENTITY_NOT_FOUND, id)
        ));
        return new ProductDto(product, product.getCategories());
    }

    @Transactional
    public ProductDto insert(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product, "id");
        product = productRepository.save(product);
        return new ProductDto(product);
    }

    @Transactional
    public ProductDto update(Long id, ProductDto productDto) {
        try {
            Product product = productRepository.getReferenceById(id);
            BeanUtils.copyProperties(productDto, product, "id");
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
}