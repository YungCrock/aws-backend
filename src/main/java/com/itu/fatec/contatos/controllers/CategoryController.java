package com.itu.fatec.contatos.controllers;

import java.util.List;
import com.itu.fatec.contatos.entities.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itu.fatec.contatos.services.CategoryService;

@RestController
@CrossOrigin
@RequestMapping("categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping("{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getAll();
    }

}
