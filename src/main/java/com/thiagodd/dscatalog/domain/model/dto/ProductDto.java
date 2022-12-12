package com.thiagodd.dscatalog.domain.model.dto;

import com.thiagodd.dscatalog.domain.model.Product;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link com.thiagodd.dscatalog.domain.model.Product} entity
 */
@Data
@NoArgsConstructor
public class ProductDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5496122317713441552L;

    private Long id;

    @Size(min = 5, max = 60, message = "O tamanho do nome deve ser entre 5 e 60 caracteres")
    @NotBlank(message = "Campo obrigatório")
    private String name;
    private String description;

    @Positive(message = "O preço deve ser um valor positivo")
    private Double price;
    private String imgUrl;

    @PastOrPresent(message = "A data do produto não pode ser futura.")
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