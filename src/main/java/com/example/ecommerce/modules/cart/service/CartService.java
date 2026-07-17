package com.example.ecommerce.modules.cart.service;

import com.example.ecommerce.modules.address.mapper.AddressMapper;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.cart.dto.CartDetailsResponse;
import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.dto.CartResponseDTO;
import com.example.ecommerce.modules.cart.exception.*;
import com.example.ecommerce.modules.cart.mapper.CartMapper;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartEnum;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.customers.exception.CustomerNotFoundException;
import com.example.ecommerce.modules.customers.mapper.CustomerMapper;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.repository.CustomerRepository;
import com.example.ecommerce.modules.product.mapper.ProductMapper;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CartService extends CartMapper {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    public CartService(CartRepository cartRepository,  CustomerRepository customerRepository,  ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Cart createCart(Integer customerId) {

        Customers customer = customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Cliente não encontrado"));
        if (customer.getCarts().stream().anyMatch(c -> c.getStatus().equals(CartEnum.ATIVO))) {
            throw new CartIsActiveFromCustomer("Usuário tem um carrinho ATIVO");
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
                .orElseThrow(() -> new CartNotFoundException("Carrinho não encontrado"));

        return cart.getItems()
                .stream()
                .map(item -> new CartItemResponseDTO(
                        item.getId(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubtotal(),
                        conversorProductDTO(item))).toList();
    }

    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    public Cart clearCart(Integer cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new CartNotFoundException("Carrinho não encontrado"));
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();

            product.setQuantidadeEstoque(
                    product.getQuantidadeEstoque() + item.getQuantidade()
            );

            product.setQuantidadeReservada(
                    product.getQuantidadeReservada() - item.getQuantidade()
            );

            productRepository.save(product);
        }

        cart.getItems().clear();
        cart.setValorTotal(BigDecimal.ZERO);

        cartRepository.save(cart);

        return cart;
    }

    public void deleteCart(Integer cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new CartNotFoundException("Carrinho não existente")
        );
        if (cart.getStatus() != CartEnum.ATIVO) {
            throw new CartAlreadyAbandonedException("Só é possível deletar carrrinhos com Status Ativo!");
        }
        cart.setStatus(CartEnum.ABANDONADO);
        clearCart(cartId);
        cartRepository.save(cart);
    }

    public CartDetailsResponse getDetailsByCart(Integer cartId, Integer customerId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new CartNotFoundException("Carrinho não existe")
        );

        if (!cart.getUsuario().getId().equals(customerId)) {
            throw new CartOwnershipException("Esse carrinho não pertence a esse usuário.");
        }

        Address enderecoPrincipal = cart.getUsuario()
                .getEnderecos()
                .stream()
                .filter(address -> Boolean.TRUE.equals(address.getEnderecoPrincipal()))
                .findFirst()
                .orElse(null);

        List<CartItemResponseDTO> items = cart.getItems()
                .stream()
                .map(item -> new CartItemResponseDTO(
                        item.getId(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubtotal(),
                        ProductMapper.toProductResponseResumeDTO(item.getProduct())
                ))
                .toList();

        assert enderecoPrincipal != null;
        return new CartDetailsResponse(
                items,
                new CartResponseDTO(
                        cart.getId(),
                        cart.getStatus(),
                        cart.getValorTotal(),
                        cart.getDataCriacao(),
                        cart.getDataAtualizacao(),
                        cart.getUsuario().getId()
                ),
                CustomerMapper.toCustomerResponseDTO(cart.getUsuario()),
                AddressMapper.toAddressList(enderecoPrincipal)
        );
    }

}
