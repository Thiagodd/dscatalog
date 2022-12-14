package com.thiagodd.dscatalog.domain.service;

import com.thiagodd.dscatalog.domain.model.Category;
import com.thiagodd.dscatalog.domain.model.dto.CategoryDto;
import com.thiagodd.dscatalog.domain.repository.CategoryRepository;
import com.thiagodd.dscatalog.domain.service.exceptions.DatabaseIntegratyViolationException;
import com.thiagodd.dscatalog.domain.service.exceptions.ResourceNotFoundException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class CategoryService {

    String MSG_ENTITY_NOT_FOUND = "[CUSTOM] Categoria com id %d não foi encontrada!";
    String MSG_ENTITY_IN_USE = "[CUSTOM] Categoria com id %d não pode ser deletada, pois está em uso!";

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDto> findAll(Pageable pageable) {
        Page<Category> categoryList = categoryRepository.findAll(pageable);
        return categoryList.map(CategoryDto::new);
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format(MSG_ENTITY_NOT_FOUND, id)
        ));
        return new CategoryDto(category);
    }

    @Transactional
    public CategoryDto insert(CategoryDto categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category, "id");
        category = categoryRepository.save(category);
        return new CategoryDto(category);
    }

    @Transactional
    public CategoryDto update(Long id, CategoryDto CategoryDto) {
        try {
            Category category = categoryRepository.getOne(id);
            BeanUtils.copyProperties(CategoryDto, category, "id");
            category = categoryRepository.save(category);
            return new CategoryDto(category);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        }
    }

    public void deleteById(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, id));
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseIntegratyViolationException(String.format(MSG_ENTITY_IN_USE, id));
        }
    }
}
