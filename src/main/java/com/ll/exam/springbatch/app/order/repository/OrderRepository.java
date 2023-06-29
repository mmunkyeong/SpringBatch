package com.ll.exam.springbatch.app.order.repository;

import com.ll.exam.springbatch.app.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}