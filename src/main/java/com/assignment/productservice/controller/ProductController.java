package com.assignment.productservice.controller;

import com.assignment.productservice.dto.ProductRequestDto;
import com.assignment.productservice.entity.Product;
import com.assignment.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> insertProductByCustomerName(@RequestBody ProductRequestDto productRequestDto) {
        productService.insertProductByCustomerName(productRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> SearchProductByCustomerName(@RequestParam String customerName) {
        return new ResponseEntity<>(productService.SearchProductByCustomerName(customerName), HttpStatus.OK);
    }
}
