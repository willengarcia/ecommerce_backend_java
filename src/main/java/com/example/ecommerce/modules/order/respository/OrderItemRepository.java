package com.example.ecommerce.modules.order.respository;

import com.example.ecommerce.modules.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
