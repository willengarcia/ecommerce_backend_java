package com.example.ecommerce.modules.cart.service;

import com.example.ecommerce.modules.cart.dto.CartItemAddProductDTO;
import com.example.ecommerce.modules.cart.dto.CartItemCreateDTO;
import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.mapper.CartMapper;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.repository.CartItemRepository;
import com.example.ecommerce.modules.cart.repository.CartRepository;
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

    public CartItemResponseDTO create(CartItemCreateDTO dto) {

        Cart cart = cartRepository.findById(dto.cartId())
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));


        CartItem created = new CartItem();

        CartItem existente = cartItemRepository
                .findByCarroIdAndProductId(dto.cartId(), dto.productId());

        if (existente != null) {
            // Verifica Estoque
            if (product.getQuantidadeEstoque() <= 0){
                throw new  RuntimeException("Quantidade de estoque insuficiente");
            }
            existente.setQuantidade(existente.getQuantidade() + 1);
            existente.setSubtotal(existente.getQuantidade() * existente.getPrecoUnitario());
            existente.setDataAtualizacao(LocalDate.now());

            cartItemRepository.save(existente);

            // Atualiza estoque
            product.setQuantidadeEstoque(product.getQuantidadeEstoque() - 1);
            productRepository.save(product);

            cart.setValorTotal(findSubTotalItemsInCart(dto.cartId()));
            cartRepository.save(cart);

            return new CartItemResponseDTO(
                    existente.getId(),
                    existente.getQuantidade(),
                    existente.getPrecoUnitario(),
                    existente.getSubtotal(),
                    conversorProductDTO(existente)
            );
        }
        if (product.getQuantidadeEstoque() <= 0){
            throw new  RuntimeException("Quantidade de estoque insuficiente");
        }

        created.setCarro(cart);
        created.setProduct(product);
        created.setQuantidade(1);
        created.setPrecoUnitario(product.getPreco());
        created.setSubtotal(product.getPreco());
        created.setDataCriacao(LocalDate.now());
        created.setDataAtualizacao(LocalDate.now());

        cart.getItems().add(created);

        cartItemRepository.save(created);

        cart.setValorTotal(
                cart.getItems()
                        .stream()
                        .map(CartItem::getSubtotal)
                        .reduce(0f, Float::sum)
        );

        cartRepository.save(cart);

        product.setQuantidadeEstoque(product.getQuantidadeEstoque() - 1);
        productRepository.save(product);

        return new CartItemResponseDTO(
                created.getId(),
                created.getQuantidade(),
                created.getPrecoUnitario(),
                created.getSubtotal(),
                conversorProductDTO(created)
        );

    }

    public CartItemResponseDTO addItems(CartItemAddProductDTO dto, Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (product.getQuantidadeEstoque() <= 0) {
            throw new RuntimeException("Estoque insuficiente");
        }

        CartItem item = cartItemRepository.findByCarroIdAndProductId(cartId, dto.productId());

        if (item != null) {
            item.setQuantidade(item.getQuantidade() + 1);
            item.setSubtotal(item.getQuantidade() * item.getPrecoUnitario());
            item.setDataAtualizacao(LocalDate.now());
        } else {
            item = new CartItem();

            item.setCarro(cart);
            item.setProduct(product);
            item.setQuantidade(1);
            item.setPrecoUnitario(product.getPreco());
            item.setSubtotal(product.getPreco());
            item.setDataCriacao(LocalDate.now());
            item.setDataAtualizacao(LocalDate.now());
        }

        cartItemRepository.save(item);

        product.setQuantidadeEstoque(product.getQuantidadeEstoque() - 1);
        productRepository.save(product);

        cart.setValorTotal(findSubTotalItemsInCart(cartId));
        cartRepository.save(cart);

        return new CartItemResponseDTO(
                item.getId(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubtotal(),
                conversorProductDTO(item)
        );
    }

    // Somar todos os subtotais de cada cartitem e adicionar no Cart
    public Float findSubTotalItemsInCart(Integer cartId) {
        Float cartItem = cartItemRepository.findAllByCarroId(cartId).stream().map(CartItem::getSubtotal).reduce(0f, Float::sum);;
        return cartItem;
    }

    public CartItem deleteItem(Integer cartId, Integer cartItemId){
        CartItem cartItem = cartItemRepository.findByCarroIdAndId(cartId, cartItemId);

        Cart cart = cartRepository.findById(cartId).orElseThrow();

        cart.getItems().remove(cartItem);
        cartItem.setCarro(null);
        cart.setValorTotal(
                cart.getItems()
                        .stream()
                        .map(CartItem::getSubtotal)
                        .reduce(0f, Float::sum)
        );
        cartRepository.save(cart);
        return null;
    }
}
