package com.thiagodd.dscatalog.api.controller;

import com.thiagodd.dscatalog.domain.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1L, "Books"));
        categoryList.add(new Category(2L, "Electronics"));
        return ResponseEntity.ok().body(categoryList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(new Category(1L, "Books"));
    }
}
