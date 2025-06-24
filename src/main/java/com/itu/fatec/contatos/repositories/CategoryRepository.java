package com.itu.fatec.contatos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itu.fatec.contatos.entities.Category;

public interface CategoryRepository extends JpaRepository <Category,Long>{
    
}
