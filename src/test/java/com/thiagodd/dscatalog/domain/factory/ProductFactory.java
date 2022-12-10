package com.thiagodd.dscatalog.domain.factory;

import com.thiagodd.dscatalog.domain.model.Category;
import com.thiagodd.dscatalog.domain.model.Product;
import com.thiagodd.dscatalog.domain.model.dto.ProductDto;

import java.time.Instant;

public class ProductFactory {

    public static Product createProduct(){
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
        product.getCategories().add(new Category(2L, "Eletronics"));
        return product;
    }

    public static ProductDto createProductDTO(){
        return new ProductDto(createProduct());
    }
}
