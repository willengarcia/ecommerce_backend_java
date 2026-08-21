package com.example.ecommerce.modules.cart.service;

import com.example.ecommerce.modules.cart.dto.CartItemCreateDTO;
import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.exception.CartAlreadyAbandonedException;
import com.example.ecommerce.modules.cart.exception.CartItemNotFoundException;
import com.example.ecommerce.modules.cart.exception.CartNotFoundException;
import com.example.ecommerce.modules.cart.mapper.CartMapper;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartEnum;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.repository.CartItemRepository;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.product.exception.InsufficientStockException;
import com.example.ecommerce.modules.product.exception.ProductNotFoundException;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Transactional
    public CartItemResponseDTO createItem(CartItemCreateDTO dto) {

        Cart cart = cartRepository.findById(dto.cartId())
                .orElseThrow(() -> new CartNotFoundException("Carrinho não encontrado"));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

        CartItem existente = cartItemRepository
                .findByCarroIdAndProductId(dto.cartId(), dto.productId());
        if(cart.getStatus().equals(CartEnum.CONVERTIDO) || cart.getStatus().equals(CartEnum.ABANDONADO)) {
            throw new CartAlreadyAbandonedException("Carrinho com status ABANDONADO ou CONVERTIDO");
        }
        if (existente != null) {
            // Verifica Estoque
            return addExistingProduct(product, existente, cart, dto);
        }
        if (product.getQuantidadeEstoque() <= 0){
            throw new InsufficientStockException("Quantidade de estoque insuficiente");
        }
        return createProductInCartItem(cart, product);

    }

    @Transactional
    public CartItemResponseDTO createProductInCartItem(Cart cart, Product product) {
        if(cart.getStatus().equals(CartEnum.CONVERTIDO) || cart.getStatus().equals(CartEnum.ABANDONADO)) {
            throw new CartAlreadyAbandonedException("Carrinho com status ABANDONADO ou CONVERTIDO");
        }
        CartItem created = new CartItem();
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
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        cartRepository.save(cart);

        return new CartItemResponseDTO(
                created.getId(),
                created.getQuantidade(),
                created.getPrecoUnitario(),
                created.getSubtotal(),
                conversorProductDTO(created));
    }

    @Transactional
    public CartItemResponseDTO addExistingProduct(Product product, CartItem cartExist, Cart cart, CartItemCreateDTO dto) {
        if (product.getQuantidadeEstoque() <= 0){
            throw new InsufficientStockException("Quantidade de estoque insuficiente");
        }
        if(cart.getStatus().equals(CartEnum.CONVERTIDO) || cart.getStatus().equals(CartEnum.ABANDONADO)) {
            throw new CartAlreadyAbandonedException("Carrinho com status ABANDONADO ou CONVERTIDO");
        }
        cartExist.setQuantidade(cartExist.getQuantidade() + 1);
        cartExist.setSubtotal(cartExist.getPrecoUnitario().multiply(BigDecimal.valueOf(cartExist.getQuantidade())));
        cartExist.setDataAtualizacao(LocalDate.now());

        cartItemRepository.save(cartExist);
        cart.setValorTotal(findSubTotalItemsInCart(dto.cartId()));
        cartRepository.save(cart);

        return new CartItemResponseDTO(
                cartExist.getId(),
                cartExist.getQuantidade(),
                cartExist.getPrecoUnitario(),
                cartExist.getSubtotal(),
                conversorProductDTO(cartExist));
    }

    public BigDecimal findSubTotalItemsInCart(Integer cartId) {
        return cartItemRepository.findAllByCarroId(cartId).stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public void deleteItem(Integer cartId, Integer cartItemId){
        CartItem cartItem = cartItemRepository.findByCarroIdAndId(cartId, cartItemId);

        if (cartItem == null) {
            throw new CartItemNotFoundException("Item não encontrado no carrinho");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Carrinho não encontrado"));

        if(!cart.getStatus().equals(CartEnum.ATIVO)) {
            throw new CartAlreadyAbandonedException("Carrinho com status ABANDONADO ou CONVERTIDO");
        }

        cart.getItems().remove(cartItem);
        cartItem.setCarro(null);
        cart.setValorTotal(
                cart.getItems()
                        .stream()
                        .map(CartItem::getSubtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        cartRepository.save(cart);
    }
}
