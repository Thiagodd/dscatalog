package com.thiagodd.dscatalog.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thiagodd.dscatalog.domain.model.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link com.thiagodd.dscatalog.domain.model.Category} entity
 */
@Data
@NoArgsConstructor
public class CategoryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4001519653170415351L;

    private Long id;
    private String name;

    private List<ProductDto> productDtos = new ArrayList<>();

    public CategoryDto(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}