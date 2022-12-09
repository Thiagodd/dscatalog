package com.thiagodd.dscatalog.domain.repository;

import com.thiagodd.dscatalog.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}