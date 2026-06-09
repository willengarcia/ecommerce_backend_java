package com.example.ecommerce.modules.order.respository;

import com.example.ecommerce.modules.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
