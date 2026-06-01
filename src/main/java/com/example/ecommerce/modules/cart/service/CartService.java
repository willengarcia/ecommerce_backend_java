package com.example.ecommerce.modules.cart.service;

import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    public CartService(CartRepository cartRepository,  CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }
    public Cart createCart(Integer customerId) {

        Customers customer = customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado"));

        Cart cart = new Cart();
        cart.setUsuario(customer);
        cart.setStatus(true);
        cart.setDataCriacao(LocalDate.now());
        cart.setDataAtualizacao(LocalDate.now());

        return cartRepository.save(cart);
    }
}
