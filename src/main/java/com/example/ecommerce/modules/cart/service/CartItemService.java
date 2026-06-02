package com.example.ecommerce.modules.cart.service;

import com.example.ecommerce.modules.cart.dto.CartItemCreateDTO;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.repository.CartItemRepository;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public CartItem create(CartItemCreateDTO dto) {

        Cart cart = cartRepository.findById(dto.cartId())
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        CartItem created = new CartItem();
        created.setCarro(cart);
        created.setProduct(product);
        created.setQuantidade(1);
        created.setPrecoUnitario(product.getPreco());
        created.setSubtotal(product.getPreco() * 1);
        created.setDataCriacao(LocalDate.now());
        created.setDataAtualizacao(LocalDate.now());

        return cartItemRepository.save(created);
    }
}
