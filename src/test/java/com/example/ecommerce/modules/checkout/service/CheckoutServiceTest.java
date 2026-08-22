package com.example.ecommerce.modules.checkout.service;

import com.example.ecommerce.modules.address.exception.AddressNotFoundException;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.cart.exception.CartNotFoundException;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartEnum;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.checkout.dto.CheckoutRequestDTO;
import com.example.ecommerce.modules.customer.model.Customer;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.model.OrderItem;
import com.example.ecommerce.modules.order.repository.OrderItemRepository;
import com.example.ecommerce.modules.order.service.OrderService;
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
class CheckoutServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ValidateProduct validateProduct;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CheckoutService checkoutService;


    @Test
    @DisplayName("Deve retornar o carrinho quando o ID informado existir")
    void returnCartWhenCartExists() {

        Cart cart = new Cart();

        when(cartRepository.findById(1))
                .thenReturn(Optional.of(cart));

        Cart resultado = checkoutService.findCartId(1);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(cart, resultado);

        verify(cartRepository).findById(1);
    }


    @Test
    @DisplayName("Deve lançar CartNotFoundException quando o carrinho não existir")
    void returnExceptionCartNotFound() {

        when(cartRepository.findById(1))
                .thenReturn(Optional.empty());

        CartNotFoundException exception = Assertions.assertThrows(
                CartNotFoundException.class,
                () -> checkoutService.findCartId(1)
        );

        Assertions.assertEquals(
                "Carrinho não encontrado",
                exception.getMessage()
        );

        verify(cartRepository).findById(1);
    }


    @Test
    @DisplayName("Deve retornar o endereço quando o ID informado existir")
    void returnAddressWhenAddressExists() {

        Address address = new Address();

        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        Address resultado = checkoutService.findAddressId(1);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(address, resultado);

        verify(addressRepository).findById(1);
    }


    @Test
    @DisplayName("Deve lançar AddressNotFoundException quando o endereço não existir")
    void returnExceptionAddressNotFound() {

        when(addressRepository.findById(1))
                .thenReturn(Optional.empty());

        AddressNotFoundException exception = Assertions.assertThrows(
                AddressNotFoundException.class,
                () -> checkoutService.findAddressId(1)
        );

        Assertions.assertEquals(
                "Endererço não encontrado",
                exception.getMessage()
        );

        verify(addressRepository).findById(1);
    }


    @Test
    @DisplayName("Deve criar OrderItem utilizando o preço atual do produto e calcular o subtotal")
    void createOrderItemCalculateSubtotal() {

        // Produto
        Product product = new Product();
        product.setNome("Notebook");
        product.setSku("NOTE-001");

        // Preço atual do produto
        product.setPreco(
                new BigDecimal("1500.00")
        );

        product.setPrecoPromocional(null);

        // Item do carrinho
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantidade(2);

        // Preço antigo salvo no carrinho
        cartItem.setPrecoUnitario(
                new BigDecimal("1200.00")
        );

        // Carrinho
        Cart cart = new Cart();

        cart.setItems(
                new ArrayList<>(List.of(cartItem))
        );

        // Pedido
        Order order = new Order();

        // Execução
        List<OrderItem> resultado =
                checkoutService.createOrderItem(
                        order,
                        cart
                );

        // Deve existir apenas um item
        Assertions.assertEquals(
                1,
                resultado.size()
        );

        OrderItem orderItem = resultado.get(0);

        // Nome
        Assertions.assertEquals(
                "Notebook",
                orderItem.getNomeProduto()
        );

        // SKU
        Assertions.assertEquals(
                "NOTE-001",
                orderItem.getSkuProduto()
        );

        // Quantidade
        Assertions.assertEquals(
                2,
                orderItem.getQuantidade()
        );

        // Deve utilizar o preço atual do Product
        Assertions.assertEquals(
                new BigDecimal("1500.00"),
                orderItem.getPrecoUnitario()
        );

        // 1500 * 2 = 3000
        Assertions.assertEquals(
                new BigDecimal("3000.00"),
                orderItem.getSubTotal()
        );

        Assertions.assertEquals(
                product,
                orderItem.getProduct()
        );

        Assertions.assertEquals(
                order,
                orderItem.getOrder()
        );

        verify(orderItemRepository)
                .save(any(OrderItem.class));
    }


    @Test
    @DisplayName("Deve finalizar a compra, reservar estoque, calcular o pedido e converter o carrinho")
    void completePurchaseSuccessfully() {

        // Cliente
        Customer customer = new Customer();
        customer.setId(1);

        // Endereço
        Address address = new Address();
        address.setId(1);
        address.setCustomer(customer);

        // Produto
        Product product = new Product();
        product.setNome("Notebook");
        product.setSku("NOTE-001");
        product.setQuantidadeEstoque(10);
        product.setQuantidadeReservada(2);

        product.setPreco(
                new BigDecimal("100.00")
        );

        product.setPrecoPromocional(null);

        // Item do carrinho
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantidade(3);

        cartItem.setPrecoUnitario(
                new BigDecimal("100.00")
        );

        // Carrinho
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCustomer(customer);
        cart.setStatus(CartEnum.ATIVO);

        cart.setItems(
                new ArrayList<>(List.of(cartItem))
        );

        // Pedido
        Order order = new Order();
        order.setValorTotal(BigDecimal.ZERO);

        // DTO
        CheckoutRequestDTO dto =
                new CheckoutRequestDTO(
                        1,
                        1
                );

        // Mock - busca do carrinho
        when(cartRepository.findById(1))
                .thenReturn(Optional.of(cart));

        // Mock - busca do endereço
        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        // Mock - criação do pedido
        when(orderService.createOrder(any()))
                .thenReturn(order);

        // Mock - persistência do OrderItem
        when(orderItemRepository.save(any(OrderItem.class)))
                .thenAnswer(
                        invocation -> invocation.getArgument(0)
                );

        // Execução
        Order resultado =
                checkoutService.completePurchase(
                        dto,
                        1
                );

        // Pedido deve ser criado
        Assertions.assertNotNull(resultado);

        // Estoque físico NÃO deve diminuir durante o checkout
        Assertions.assertEquals(
                10,
                product.getQuantidadeEstoque()
        );

        // Reservado:
        // 2 existentes + 3 do checkout = 5
        Assertions.assertEquals(
                5,
                product.getQuantidadeReservada()
        );

        // Carrinho deve ser convertido
        Assertions.assertEquals(
                CartEnum.CONVERTIDO,
                cart.getStatus()
        );

        // Pedido deve possuir um item
        Assertions.assertEquals(
                1,
                resultado.getOrderItem().size()
        );

        // Preço oficial deve ser o preço atual do produto
        Assertions.assertEquals(
                new BigDecimal("100.00"),
                resultado.getOrderItem()
                        .get(0)
                        .getPrecoUnitario()
        );

        // Subtotal:
        // 100 * 3 = 300
        Assertions.assertEquals(
                new BigDecimal("300.00"),
                resultado.getOrderItem()
                        .get(0)
                        .getSubTotal()
        );

        // Total do pedido deve ser calculado
        // através dos OrderItems
        Assertions.assertEquals(
                new BigDecimal("300.00"),
                resultado.getValorTotal()
        );

        // Validação do estoque deve ser executada
        verify(validateProduct)
                .validarEstoqueProduct(cartItem);

        // Produto deve ser salvo após a reserva
        verify(productRepository)
                .save(product);

        // Pedido deve ser criado
        verify(orderService)
                .createOrder(any());

        // OrderItem deve ser salvo
        verify(orderItemRepository)
                .save(any(OrderItem.class));

        // Carrinho convertido deve ser salvo
        verify(cartRepository)
                .save(cart);
    }


    @Test
    @DisplayName("Deve utilizar o preço atual do produto no checkout e ignorar o preço antigo do carrinho")
    void shouldUseCurrentProductPriceAtCheckout() {

        // Produto
        Product product = new Product();
        product.setNome("Notebook");
        product.setSku("NOTE-001");

        // Preço atual
        product.setPreco(
                new BigDecimal("150.00")
        );

        product.setPrecoPromocional(null);

        // Item do carrinho
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantidade(2);

        // Preço antigo do carrinho
        cartItem.setPrecoUnitario(
                new BigDecimal("100.00")
        );

        // Carrinho
        Cart cart = new Cart();

        cart.setItems(
                new ArrayList<>(List.of(cartItem))
        );

        // Pedido
        Order order = new Order();

        // Execução
        List<OrderItem> items =
                checkoutService.createOrderItem(
                        order,
                        cart
                );

        OrderItem orderItem = items.get(0);

        // Deve ignorar os R$ 100 do carrinho
        // e utilizar os R$ 150 atuais do produto
        Assertions.assertEquals(
                new BigDecimal("150.00"),
                orderItem.getPrecoUnitario()
        );

        // 150 * 2 = 300
        Assertions.assertEquals(
                new BigDecimal("300.00"),
                orderItem.getSubTotal()
        );

        verify(orderItemRepository)
                .save(any(OrderItem.class));
    }


    @Test
    @DisplayName("Deve utilizar o preço promocional quando o produto possuir promoção")
    void shouldUsePromotionalProductPriceAtCheckout() {

        // Produto
        Product product = new Product();
        product.setNome("Notebook");
        product.setSku("NOTE-001");

        // Preço normal
        product.setPreco(
                new BigDecimal("150.00")
        );

        // Preço promocional
        product.setPrecoPromocional(
                new BigDecimal("120.00")
        );

        // Item do carrinho
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantidade(2);

        // Preço antigo do carrinho
        cartItem.setPrecoUnitario(
                new BigDecimal("100.00")
        );

        // Carrinho
        Cart cart = new Cart();

        cart.setItems(
                new ArrayList<>(List.of(cartItem))
        );

        // Pedido
        Order order = new Order();

        // Execução
        List<OrderItem> items =
                checkoutService.createOrderItem(
                        order,
                        cart
                );

        OrderItem orderItem = items.get(0);

        // Deve utilizar o preço promocional
        Assertions.assertEquals(
                new BigDecimal("120.00"),
                orderItem.getPrecoUnitario()
        );

        // 120 * 2 = 240
        Assertions.assertEquals(
                new BigDecimal("240.00"),
                orderItem.getSubTotal()
        );

        verify(orderItemRepository)
                .save(any(OrderItem.class));
    }
}