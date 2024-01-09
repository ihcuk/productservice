package com.assignment.productservice.repository;

import com.assignment.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    public List<Product> findByUserid(Integer userId);
}
