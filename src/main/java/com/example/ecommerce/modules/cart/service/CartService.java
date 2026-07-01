package com.example.ecommerce.modules.cart.service;

import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.exception.CartException;
import com.example.ecommerce.modules.cart.mapper.CartMapper;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartEnum;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.repository.CustomerRepository;
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
                        new CartException("Cliente não encontrado"));
        if (customer.getCarts().stream().anyMatch(c -> c.isStatus().equals(CartEnum.ATIVO))) {
            throw new CartException("Usuário tem um carrinho ATIVO");
        }
        Cart cart = new Cart();
        cart.setUsuario(customer);
        cart.setStatus(CartEnum.ATIVO);
        cart.setDataCriacao(LocalDate.now());
        cart.setDataAtualizacao(LocalDate.now());

        return cartRepository.save(cart);
    }

    public List<CartItemResponseDTO> getItemsByCart(Integer cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartException("Carrinho não encontrado"));

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
        return cartRepository.findAll();
    }

    public Cart clearCart(Integer cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new CartException("Carrinho não encontrado"));
        cart.getItems().clear();
        cart.setValorTotal(0f);
        cartRepository.save(cart);
        return cart;
    }
    public void deleteCart(Integer cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new CartException("Carrinho não existente")
        );
        cartRepository.delete(cart);
    }
}
