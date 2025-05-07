package com.example.hello_spring.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hello_spring.models.ProductModel;

@Repository
public interface ProductRespository extends JpaRepository<ProductModel, UUID>{
    
}
