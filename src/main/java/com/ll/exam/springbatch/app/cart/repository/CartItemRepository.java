package com.ll.exam.springbatch.app.cart.repository;

import com.ll.exam.springbatch.app.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}