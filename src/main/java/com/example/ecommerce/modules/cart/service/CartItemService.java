package com.example.ecommerce.modules.cart.service;

import com.example.ecommerce.modules.cart.dto.CartItemCreateDTO;
import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.exception.CartException;
import com.example.ecommerce.modules.cart.mapper.CartMapper;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.repository.CartItemRepository;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.customers.mapper.CustomerMapper;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
                .orElseThrow(() -> new CartException("Carrinho não encontrado"));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new CartException("Produto não encontrado"));

        CartItem existente = cartItemRepository
                .findByCarroIdAndProductId(dto.cartId(), dto.productId());

        if (existente != null) {
            // Verifica Estoque
            return adicionarProdutoExistente(product, existente, cart, dto);
        }
        if (product.getQuantidadeEstoque() <= 0){
            throw new CartException("Quantidade de estoque insuficiente");
        }
        return criarProdutoInCartItem(cart, product);

    }

    public CartItemResponseDTO criarProdutoInCartItem(Cart cart, Product product) {
        CartItem created = new CartItem();
        created.setCarro(cart);
        created.setProduct(product);
        created.setQuantidade(1);
        product.setQuantidadeReservada(product.getQuantidadeReservada() + 1);
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
                conversorProductDTO(created));
    }

    public CartItemResponseDTO adicionarProdutoExistente(Product product, CartItem existente, Cart cart, CartItemCreateDTO dto) {
        if (product.getQuantidadeEstoque() <= 0){
            throw new CartException("Quantidade de estoque insuficiente");
        }
        existente.setQuantidade(existente.getQuantidade() + 1);
        existente.setSubtotal(existente.getQuantidade() * existente.getPrecoUnitario());
        existente.setDataAtualizacao(LocalDate.now());

        cartItemRepository.save(existente);

        // Atualiza estoque
        product.setQuantidadeEstoque(product.getQuantidadeEstoque() - 1);
        product.setQuantidadeReservada(existente.getQuantidade());
        productRepository.save(product);

        cart.setValorTotal(findSubTotalItemsInCart(dto.cartId()));
        cartRepository.save(cart);

        return new CartItemResponseDTO(
                existente.getId(),
                existente.getQuantidade(),
                existente.getPrecoUnitario(),
                existente.getSubtotal(),
                conversorProductDTO(existente));
    }

    public Float findSubTotalItemsInCart(Integer cartId) {
        Float cartItem = cartItemRepository.findAllByCarroId(cartId).stream().map(CartItem::getSubtotal).reduce(0f, Float::sum);;
        return cartItem;
    }

    public CartItem deleteItem(Integer cartId, Integer cartItemId){
        CartItem cartItem = cartItemRepository.findByCarroIdAndId(cartId, cartItemId);

        if (cartItem == null) {
            throw new CartException("Item não encontrado no carrinho");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartException("Carrinho não encontrado"));

        Product product = productRepository.findById(cartItem.getProduct().getId());
        product.setQuantidadeEstoque(product.getQuantidadeEstoque() + cartItem.getQuantidade());
        product.setQuantidadeReservada(product.getQuantidadeReservada() - cartItem.getQuantidade());
        cart.getItems().remove(cartItem);
        cartItem.setCarro(null);
        cart.setValorTotal(
                cart.getItems()
                        .stream()
                        .map(CartItem::getSubtotal)
                        .reduce(0f, Float::sum)
        );
        productRepository.save(product);
        cartRepository.save(cart);
        return null;
    }
}
