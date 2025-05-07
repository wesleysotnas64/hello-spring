package com.example.hello_spring.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.hello_spring.dtos.ProductRecordDto;
import com.example.hello_spring.models.ProductModel;
import com.example.hello_spring.repositories.ProductRespository;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class ProductController {
    @Autowired
    ProductRespository productRespository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {

        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        

        return ResponseEntity.status(HttpStatus.CREATED).body(productRespository.save(productModel));

    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productRespository.findAll());
    }
    
    @GetMapping("products/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable(value="id") UUID id) {
        Optional<ProductModel> product = productRespository.findById(id);

        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(product.get());
        }
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> product = productRespository.findById(id);

        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        } else {
            var productModel = product.get();
            BeanUtils.copyProperties(productRecordDto, productModel);
            return ResponseEntity.status(HttpStatus.OK).body(productRespository.save(productModel));
        }
    }
    
    @DeleteMapping("products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id) {
        Optional<ProductModel> product = productRespository.findById(id);

        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        } else {
            productRespository.delete(product.get());
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfuly.");
        }
    }

}
