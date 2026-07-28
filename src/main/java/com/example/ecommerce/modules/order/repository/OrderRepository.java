package com.example.ecommerce.modules.order.repository;

import com.example.ecommerce.modules.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUsuarioId(Long userId);
}
