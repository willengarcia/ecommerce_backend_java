package com.example.ecommerce.modules.cart.service;

import com.example.ecommerce.modules.cart.dto.CartItemAddProductDTO;
import com.example.ecommerce.modules.cart.dto.CartItemCreateDTO;
import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.mapper.CartMapper;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.repository.CartItemRepository;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.product.dto.ProductDTO;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CartItemService extends CartMapper {
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
        cart.setValorTotal(product.getPreco());
        created.setCarro(cart);
        created.setProduct(product);
        created.setQuantidade(1);
        created.setPrecoUnitario(product.getPreco());
        created.setSubtotal(product.getPreco() * 1);
        created.setDataCriacao(LocalDate.now());
        created.setDataAtualizacao(LocalDate.now());
        cartRepository.save(cart);
        return cartItemRepository.save(created);
    }

    public CartItemResponseDTO addItems(CartItemAddProductDTO dto, Integer cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("CartItemId não encontrado!"));
        List<CartItem> cartItem = cart.getItems();
        List<Product> products = cartItem.stream().map(cartItens -> cartItens.getProduct()).toList();
        if(products.stream().anyMatch(product -> product.getId().equals(dto.productId()))) {
            for(CartItem c: cartItem){
                if(c.getProduct().getId().equals(dto.productId())) {
                    c.setQuantidade(c.getQuantidade() + 1);
                    c.setSubtotal(c.getQuantidade()*c.getPrecoUnitario());
                    cartItemRepository.save(c);
                }
            }
            CartItem cartItemsResponse = cartItemRepository.findAllByCarroIdAndProductId(cartId, dto.productId());

            CartItemResponseDTO dtoResponse = new CartItemResponseDTO(
                    cartItemsResponse.getId(),
                    cartItemsResponse.getQuantidade(),
                    cartItemsResponse.getPrecoUnitario(),
                    cartItemsResponse.getSubtotal(),
                    conversorProductDTO(cartItemsResponse)
            );
            cart.setValorTotal(findSubTotalItemsInCart(cartId));
            cartRepository.save(cart);
            findSubTotalItemsInCart(cartId);
            return dtoResponse;
        }else {
            return null;
        }
    }

    public Float findSubTotalItemsInCart(Integer cartId) {
        Float cartItem = cartItemRepository.findAllByCarroId(cartId).stream().map(CartItem::getSubtotal).reduce(0f, Float::sum);;
        return cartItem;
    }

}
