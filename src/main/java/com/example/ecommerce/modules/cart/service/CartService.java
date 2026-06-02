package com.example.ecommerce.modules.cart.service;

import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.model.Cart;
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

    public List<CartItemResponseDTO> getItemsByCart(Integer cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        return cart.getItems()
                .stream()
                .map(item -> {
                    Product product = item.getProduct();

                    ProductDTO productDTO = new ProductDTO(
                            product.getId(),
                            product.getNome(),
                            product.getSlug(),
                            product.getDescricaoCurta(),
                            product.getDescricao(),
                            product.getPreco(),
                            product.getPrecoPromocional(),
                            product.getQuantidadeEstoque(),
                            product.getQuantidadeReservada(),
                            product.getEstoqueMinimo(),
                            product.getSku(),
                            product.getPeso(),
                            product.getAltura(),
                            product.getLargura(),
                            product.getComprimento(),
                            product.getMediaAvaliacao(),
                            product.getTotalAvaliacoes(),
                            product.isStatus(),
                            product.getDataCriacao(),
                            product.getCategoria().getId()
                    );

                    return new CartItemResponseDTO(
                            item.getQuantidade(),
                            item.getPrecoUnitario(),
                            item.getSubtotal(),
                            productDTO
                    );
                })
                .toList();
    }

    public List<Cart> getAllCart() {
        List<Cart> cart = cartRepository.findAll();
        return cart;
    }
}
