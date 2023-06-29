package com.ll.exam.springbatch.app.product.repository;

import com.ll.exam.springbatch.app.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}