package com.thiagodd.dscatalog.domain.model.dto;

import com.thiagodd.dscatalog.domain.model.Category;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * A DTO for the {@link com.thiagodd.dscatalog.domain.model.Category} entity
 */
@Data
public class CategoryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4001519653170415351L;
    private final Long id;
    private final String name;

    public CategoryDto(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}