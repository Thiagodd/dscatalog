package com.thiagodd.dscatalog.domain.model.dto;

import com.thiagodd.dscatalog.domain.model.Category;
import com.thiagodd.dscatalog.domain.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link com.thiagodd.dscatalog.domain.model.Product} entity
 */
@Data
@NoArgsConstructor
public class ProductDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5496122317713441552L;

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;
    private Instant date;

    private List<CategoryDto> categories = new ArrayList<>();

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
        this.date = product.getDate();
        product.getCategories().forEach(cat -> this.categories.add(new CategoryDto(cat)));
    }

}