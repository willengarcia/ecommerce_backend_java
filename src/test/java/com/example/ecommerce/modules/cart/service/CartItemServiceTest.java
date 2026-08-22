package com.example.ecommerce.modules.cart.service;

import com.example.ecommerce.modules.cart.dto.CartItemCreateDTO;
import com.example.ecommerce.modules.cart.exception.CartAlreadyAbandonedException;
import com.example.ecommerce.modules.cart.exception.CartItemNotFoundException;
import com.example.ecommerce.modules.cart.exception.CartNotFoundException;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartEnum;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.repository.CartItemRepository;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.product.exception.InsufficientStockException;
import com.example.ecommerce.modules.product.exception.ProductNotFoundException;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartItemService cartItemService;


    @Test
    @DisplayName("Deve lançar CartNotFoundException quando o carrinho não existir")
    void returnExceptionWhenCartNotFound() {

        CartItemCreateDTO dto = new CartItemCreateDTO(
                1,
                10
        );

        when(cartRepository.findById(dto.cartId()))
                .thenReturn(Optional.empty());

        CartNotFoundException exception =
                Assertions.assertThrows(
                        CartNotFoundException.class,
                        () -> cartItemService.createItem(dto)
                );

        Assertions.assertEquals(
                "Carrinho não encontrado",
                exception.getMessage()
        );

        verify(cartRepository)
                .findById(dto.cartId());

        verify(productRepository, never())
                .findById((Long) any());

        verify(cartItemRepository, never())
                .save(any(CartItem.class));
    }


    @Test
    @DisplayName("Deve lançar ProductNotFoundException quando o produto não existir")
    void returnExceptionWhenProductNotFound() {

        CartItemCreateDTO dto = new CartItemCreateDTO(
                1,
                10
        );

        Cart cart = new Cart();
        cart.setStatus(CartEnum.ATIVO);

        when(cartRepository.findById(dto.cartId()))
                .thenReturn(Optional.of(cart));

        when(productRepository.findById(dto.productId()))
                .thenReturn(Optional.empty());

        ProductNotFoundException exception =
                Assertions.assertThrows(
                        ProductNotFoundException.class,
                        () -> cartItemService.createItem(dto)
                );

        Assertions.assertEquals(
                "Produto não encontrado",
                exception.getMessage()
        );

        verify(cartRepository)
                .findById(dto.cartId());

        verify(productRepository)
                .findById(dto.productId());

        verify(cartItemRepository, never())
                .save(any(CartItem.class));
    }


    @Test
    @DisplayName("Deve lançar CartAlreadyAbandonedException quando o carrinho estiver CONVERTIDO")
    void returnExceptionWhenCartIsConverted() {

        CartItemCreateDTO dto = new CartItemCreateDTO(
                1,
                10
        );

        Cart cart = new Cart();
        cart.setStatus(CartEnum.CONVERTIDO);

        Product product = new Product();
        product.setQuantidadeEstoque(10);

        when(cartRepository.findById(dto.cartId()))
                .thenReturn(Optional.of(cart));

        when(productRepository.findById(dto.productId()))
                .thenReturn(Optional.of(product));

        CartAlreadyAbandonedException exception =
                Assertions.assertThrows(
                        CartAlreadyAbandonedException.class,
                        () -> cartItemService.createItem(dto)
                );

        Assertions.assertEquals(
                "Carrinho com status ABANDONADO ou CONVERTIDO",
                exception.getMessage()
        );

        verify(cartItemRepository, never())
                .save(any(CartItem.class));

        verify(cartRepository, never())
                .save(any(Cart.class));
    }


    @Test
    @DisplayName("Deve lançar CartAlreadyAbandonedException quando o carrinho estiver ABANDONADO")
    void returnExceptionWhenCartIsAbandoned() {

        CartItemCreateDTO dto = new CartItemCreateDTO(
                1,
                10
        );

        Cart cart = new Cart();
        cart.setStatus(CartEnum.ABANDONADO);

        Product product = new Product();
        product.setQuantidadeEstoque(10);

        when(cartRepository.findById(dto.cartId()))
                .thenReturn(Optional.of(cart));

        when(productRepository.findById(dto.productId()))
                .thenReturn(Optional.of(product));

        CartAlreadyAbandonedException exception =
                Assertions.assertThrows(
                        CartAlreadyAbandonedException.class,
                        () -> cartItemService.createItem(dto)
                );

        Assertions.assertEquals(
                "Carrinho com status ABANDONADO ou CONVERTIDO",
                exception.getMessage()
        );

        verify(cartItemRepository, never())
                .save(any(CartItem.class));
    }


    @Test
    @DisplayName("Deve lançar InsufficientStockException quando o produto estiver sem estoque")
    void returnExceptionWhenProductHasNoStock() {

        CartItemCreateDTO dto = new CartItemCreateDTO(
                1,
                10
        );

        Cart cart = new Cart();
        cart.setStatus(CartEnum.ATIVO);

        Product product = new Product();
        product.setQuantidadeEstoque(0);
        product.setQuantidadeReservada(0);

        when(cartRepository.findById(dto.cartId()))
                .thenReturn(Optional.of(cart));

        when(productRepository.findById(dto.productId()))
                .thenReturn(Optional.of(product));

        when(cartItemRepository.findByCarroIdAndProductId(
                dto.cartId(),
                dto.productId()
        )).thenReturn(null);

        InsufficientStockException exception =
                Assertions.assertThrows(
                        InsufficientStockException.class,
                        () -> cartItemService.createItem(dto)
                );

        Assertions.assertEquals(
                "Quantidade de estoque insuficiente",
                exception.getMessage()
        );

        verify(cartItemRepository, never())
                .save(any(CartItem.class));

        verify(cartRepository, never())
                .save(any(Cart.class));
    }


    @Test
    @DisplayName("Deve adicionar uma unidade quando o produto já existir no carrinho")
    void addExistingProductSuccessfully() {

        Product product = new Product();
        product.setNome("Notebook");
        product.setPreco(new BigDecimal("100.00"));
        product.setQuantidadeEstoque(10);
        product.setQuantidadeReservada(2);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setStatus(CartEnum.ATIVO);
        cart.setItems(new ArrayList<>());

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setProduct(product);
        cartItem.setCarro(cart);
        cartItem.setQuantidade(2);
        cartItem.setPrecoUnitario(
                new BigDecimal("100.00")
        );
        cartItem.setSubtotal(
                new BigDecimal("200.00")
        );

        cart.getItems().add(cartItem);

        CartItemCreateDTO dto = new CartItemCreateDTO(
                1,
                10
        );

        when(cartItemRepository.findAllByCarroId(1))
                .thenReturn(List.of(cartItem));

        cartItemService.addExistingProduct(
                product,
                cartItem,
                cart,
                dto
        );

        // Quantidade:
        // 2 + 1 = 3
        Assertions.assertEquals(
                3,
                cartItem.getQuantidade()
        );

        // Subtotal:
        // 100 * 3 = 300
        Assertions.assertEquals(
                new BigDecimal("300.00"),
                cartItem.getSubtotal()
        );

        /*
         * REGRA IMPORTANTE:
         * Carrinho NÃO movimenta estoque.
         */

        Assertions.assertEquals(
                10,
                product.getQuantidadeEstoque()
        );

        Assertions.assertEquals(
                2,
                product.getQuantidadeReservada()
        );

        verify(cartItemRepository)
                .save(cartItem);

        verify(cartRepository)
                .save(cart);

        /*
         * Garante que o Product não foi persistido
         * por alteração de estoque.
         */
        verify(productRepository, never())
                .save(any(Product.class));
    }


    @Test
    @DisplayName("Deve calcular corretamente a soma dos subtotais dos itens do carrinho")
    void findSubTotalItemsInCartSuccessfully() {

        CartItem item1 = new CartItem();
        item1.setSubtotal(
                new BigDecimal("100.00")
        );

        CartItem item2 = new CartItem();
        item2.setSubtotal(
                new BigDecimal("250.00")
        );

        CartItem item3 = new CartItem();
        item3.setSubtotal(
                new BigDecimal("50.00")
        );

        when(cartItemRepository.findAllByCarroId(1))
                .thenReturn(
                        List.of(
                                item1,
                                item2,
                                item3
                        )
                );

        BigDecimal resultado =
                cartItemService.findSubTotalItemsInCart(1);

        Assertions.assertEquals(
                new BigDecimal("400.00"),
                resultado
        );

        verify(cartItemRepository)
                .findAllByCarroId(1);
    }


    @Test
    @DisplayName("Deve lançar CartItemNotFoundException quando o item não existir no carrinho")
    void returnExceptionWhenCartItemNotFound() {

        when(cartItemRepository.findByCarroIdAndId(
                1,
                99
        )).thenReturn(null);

        CartItemNotFoundException exception =
                Assertions.assertThrows(
                        CartItemNotFoundException.class,
                        () -> cartItemService.deleteItem(
                                1,
                                99
                        )
                );

        Assertions.assertEquals(
                "Item não encontrado no carrinho",
                exception.getMessage()
        );

        verify(cartRepository, never())
                .save(any(Cart.class));
    }


    @Test
    @DisplayName("Deve lançar CartNotFoundException ao excluir item quando o carrinho não existir")
    void returnExceptionWhenDeletingItemFromNonexistentCart() {

        CartItem cartItem = new CartItem();

        when(cartItemRepository.findByCarroIdAndId(
                1,
                10
        )).thenReturn(cartItem);

        when(cartRepository.findById(1))
                .thenReturn(Optional.empty());

        CartNotFoundException exception =
                Assertions.assertThrows(
                        CartNotFoundException.class,
                        () -> cartItemService.deleteItem(
                                1,
                                10
                        )
                );

        Assertions.assertEquals(
                "Carrinho não encontrado",
                exception.getMessage()
        );

        verify(cartRepository, never())
                .save(any(Cart.class));
    }


    @Test
    @DisplayName("Deve impedir exclusão de item quando o carrinho não estiver ATIVO")
    void returnExceptionWhenDeletingItemFromConvertedCart() {

        CartItem cartItem = new CartItem();

        Cart cart = new Cart();
        cart.setStatus(CartEnum.CONVERTIDO);

        when(cartItemRepository.findByCarroIdAndId(
                1,
                10
        )).thenReturn(cartItem);

        when(cartRepository.findById(1))
                .thenReturn(Optional.of(cart));

        CartAlreadyAbandonedException exception =
                Assertions.assertThrows(
                        CartAlreadyAbandonedException.class,
                        () -> cartItemService.deleteItem(
                                1,
                                10
                        )
                );

        Assertions.assertEquals(
                "Carrinho com status ABANDONADO ou CONVERTIDO",
                exception.getMessage()
        );

        verify(cartRepository, never())
                .save(cart);
    }


    @Test
    @DisplayName("Deve remover o item do carrinho sem alterar estoque ou quantidade reservada")
    void deleteItemSuccessfullyWithoutChangingStock() {

        Product product = new Product();
        product.setQuantidadeEstoque(10);
        product.setQuantidadeReservada(3);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setStatus(CartEnum.ATIVO);

        CartItem itemToDelete = new CartItem();
        itemToDelete.setId(10L);
        itemToDelete.setProduct(product);
        itemToDelete.setCarro(cart);
        itemToDelete.setQuantidade(2);
        itemToDelete.setPrecoUnitario(
                new BigDecimal("100.00")
        );
        itemToDelete.setSubtotal(
                new BigDecimal("200.00")
        );

        CartItem remainingItem = new CartItem();
        remainingItem.setId(11L);
        remainingItem.setCarro(cart);
        remainingItem.setQuantidade(1);
        remainingItem.setPrecoUnitario(
                new BigDecimal("50.00")
        );
        remainingItem.setSubtotal(
                new BigDecimal("50.00")
        );

        cart.setItems(
                new ArrayList<>(
                        List.of(
                                itemToDelete,
                                remainingItem
                        )
                )
        );

        cart.setValorTotal(
                new BigDecimal("250.00")
        );

        when(cartItemRepository.findByCarroIdAndId(
                1,
                10
        )).thenReturn(itemToDelete);

        when(cartRepository.findById(1))
                .thenReturn(Optional.of(cart));

        cartItemService.deleteItem(
                1,
                10
        );

        /*
         * O item deve sair da coleção.
         */
        Assertions.assertEquals(
                1,
                cart.getItems().size()
        );

        Assertions.assertFalse(
                cart.getItems().contains(itemToDelete)
        );

        /*
         * Relação com o carrinho deve ser removida.
         */
        Assertions.assertNull(
                itemToDelete.getCarro()
        );

        /*
         * Total deve ser recalculado apenas
         * com o item restante.
         */
        Assertions.assertEquals(
                new BigDecimal("50.00"),
                cart.getValorTotal()
        );

        /*
         * REGRA CRÍTICA:
         *
         * Remover do carrinho NÃO devolve estoque,
         * pois o carrinho nunca reservou estoque.
         */

        Assertions.assertEquals(
                10,
                product.getQuantidadeEstoque()
        );

        Assertions.assertEquals(
                3,
                product.getQuantidadeReservada()
        );

        verify(cartRepository)
                .save(cart);

        verify(productRepository, never())
                .save(any(Product.class));
    }
}