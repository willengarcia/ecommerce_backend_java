package com.example.ecommerce.modules.cart.service;

import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.mapper.CartMapper;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartEnum;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.repository.CustomerRepository;
import com.example.ecommerce.modules.product.dto.ProductDTO;
import com.example.ecommerce.modules.product.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CartService extends CartMapper {
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
        cart.setStatus(CartEnum.ATIVO);
        cart.setDataCriacao(LocalDate.now());
        cart.setDataAtualizacao(LocalDate.now());

        return cartRepository.save(cart);
    }

    public List<CartItemResponseDTO> getItemsByCart(Integer cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        return cart.getItems()
                .stream()
                .map(item -> new CartItemResponseDTO(
                        item.getId(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubtotal(),
                        conversorProductDTO(item)
                ))
                .toList();
    }

    public List<Cart> getAllCart() {
        List<Cart> cart = cartRepository.findAll();
        return cart;
    }

    public Cart clearCart(Integer cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.getItems().clear();
        cart.setValorTotal(0f);
        cartRepository.save(cart);
        return cart;
    }
}
