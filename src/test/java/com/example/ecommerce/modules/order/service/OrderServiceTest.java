package com.example.ecommerce.modules.order.service;

import com.example.ecommerce.modules.address.exception.AddressNotFoundException;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.customer.model.Customer;
import com.example.ecommerce.modules.order.dto.OrderCreateDTO;
import com.example.ecommerce.modules.order.dto.OrderUpdateAddressDTO;
import com.example.ecommerce.modules.order.exception.InvalidOrderDataException;
import com.example.ecommerce.modules.order.exception.InvalidOrderStatusException;
import com.example.ecommerce.modules.order.exception.OrderAddressOwnershipException;
import com.example.ecommerce.modules.order.exception.OrderOwnershipException;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.model.OrderEnum;
import com.example.ecommerce.modules.order.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private OrderService orderService;


    @Test
    @DisplayName("Deve lançar InvalidOrderDataException quando os dados do pedido não forem informados")
    void returnExceptionWhenOrderDataIsNull() {

        InvalidOrderDataException exception = Assertions.assertThrows(
                InvalidOrderDataException.class,
                () -> orderService.createOrder(null)
        );

        Assertions.assertEquals(
                "Os dados do pedido não foram informados.",
                exception.getMessage()
        );

        verify(orderRepository, never())
                .save(any(Order.class));
    }


    @Test
    @DisplayName("Deve criar um pedido com status CRIADO e valor total zerado")
    void createOrderSuccessfully() {

        // Cliente
        Customer customer = new Customer();
        customer.setId(1);

        // Carrinho
        Cart cart = new Cart();
        cart.setCustomer(customer);

        // Endereço
        Address address = new Address();
        address.setId(10);
        address.setCustomer(customer);
        address.setNomeEndereco("Casa");
        address.setNomeDestinatario("Will");
        address.setCep("67000000");
        address.setRua("Rua Teste");
        address.setNumero("100");
        address.setComplemento("Apto 10");
        address.setBairro("Centro");
        address.setCidade("Ananindeua");
        address.setEstado("PA");
        address.setReferencia("Próximo ao mercado");
        address.setEnderecoPrincipal(true);

        OrderCreateDTO dto = new OrderCreateDTO(
                cart,
                address,
                "PIX"
        );

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order resultado = orderService.createOrder(dto);

        Assertions.assertNotNull(resultado);

        Assertions.assertEquals(
                OrderEnum.AGUARDANDO_PAGAMENTO,
                resultado.getStatus()
        );

        Assertions.assertEquals(
                BigDecimal.ZERO,
                resultado.getValorTotal()
        );

        Assertions.assertEquals(
                customer,
                resultado.getCustomer()
        );

        Assertions.assertEquals(
                address,
                resultado.getAddress()
        );

        Assertions.assertEquals(
                address.getId(),
                resultado.getIdAddress()
        );

        Assertions.assertEquals(
                "Casa",
                resultado.getNomeEndereco()
        );

        Assertions.assertEquals(
                "Will",
                resultado.getNomeDestinatario()
        );

        Assertions.assertEquals(
                "67000000",
                resultado.getCep()
        );

        Assertions.assertEquals(
                "Rua Teste",
                resultado.getRua()
        );

        Assertions.assertEquals(
                "100",
                resultado.getNumero()
        );

        Assertions.assertEquals(
                "Ananindeua",
                resultado.getCidade()
        );

        Assertions.assertEquals(
                "PA",
                resultado.getEstado()
        );

        Assertions.assertNotNull(
                resultado.getDataCriacao()
        );

        Assertions.assertNotNull(
                resultado.getDataAtualizacao()
        );

        verify(orderRepository)
                .save(any(Order.class));
    }


    @Test
    @DisplayName("Deve listar os pedidos pertencentes ao cliente informado")
    void getOrdersByUserIdSuccessfully() {

        Order order1 = new Order();
        Order order2 = new Order();

        when(orderRepository.findByCustomerId(1L))
                .thenReturn(List.of(order1, order2));

        List<Order> resultado =
                orderService.getOrdersByUserId(1L);

        Assertions.assertEquals(
                2,
                resultado.size()
        );

        Assertions.assertEquals(
                order1,
                resultado.get(0)
        );

        Assertions.assertEquals(
                order2,
                resultado.get(1)
        );

        verify(orderRepository)
                .findByCustomerId(1L);
    }


    @Test
    @DisplayName("Deve atualizar o endereço do pedido quando cliente, endereço e status forem válidos")
    void updateOrderAddressSuccessfully() {

        // Cliente
        Customer customer = new Customer();
        customer.setId(1);

        // Pedido
        Order order = new Order();
        order.setOrderId(1L);
        order.setCustomer(customer);
        order.setStatus(OrderEnum.CRIADO);

        // Novo endereço
        Address address = new Address();
        address.setId(20);
        address.setCustomer(customer);
        address.setNomeEndereco("Trabalho");
        address.setNomeDestinatario("Will");
        address.setCep("67000000");
        address.setRua("Avenida Teste");
        address.setNumero("500");
        address.setComplemento("Sala 01");
        address.setBairro("Centro");
        address.setCidade("Ananindeua");
        address.setEstado("PA");
        address.setReferencia("Próximo ao shopping");
        address.setEnderecoPrincipal(false);

        OrderUpdateAddressDTO dto =
                new OrderUpdateAddressDTO(
                        20,
                        1
                );

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(addressRepository.findById(20))
                .thenReturn(Optional.of(address));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order resultado =
                orderService.updateOrderAddress(
                        1L,
                        dto
                );

        Assertions.assertEquals(
                address,
                resultado.getAddress()
        );

        Assertions.assertEquals(
                20,
                resultado.getIdAddress()
        );

        Assertions.assertEquals(
                "Trabalho",
                resultado.getNomeEndereco()
        );

        Assertions.assertEquals(
                "Avenida Teste",
                resultado.getRua()
        );

        Assertions.assertEquals(
                "500",
                resultado.getNumero()
        );

        Assertions.assertEquals(
                "Ananindeua",
                resultado.getCidade()
        );

        Assertions.assertEquals(
                "PA",
                resultado.getEstado()
        );

        verify(orderRepository)
                .findById(1L);

        verify(addressRepository)
                .findById(20);

        verify(orderRepository)
                .save(order);
    }


    @Test
    @DisplayName("Deve lançar AddressNotFoundException quando o novo endereço não existir")
    void returnExceptionWhenAddressNotFound() {

        Customer customer = new Customer();
        customer.setId(1);

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderEnum.AGUARDANDO_PAGAMENTO);

        OrderUpdateAddressDTO dto =
                new OrderUpdateAddressDTO(
                        999,
                        1
                );

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(addressRepository.findById(999))
                .thenReturn(Optional.empty());

        AddressNotFoundException exception =
                Assertions.assertThrows(
                        AddressNotFoundException.class,
                        () -> orderService.updateOrderAddress(
                                1L,
                                dto
                        )
                );

        Assertions.assertEquals(
                "Endereço não encontrado!",
                exception.getMessage()
        );

        verify(orderRepository)
                .findById(1L);

        verify(addressRepository)
                .findById(999);

        verify(orderRepository, never())
                .save(any(Order.class));
    }


    @Test
    @DisplayName("Deve lançar OrderOwnershipException quando o pedido não pertencer ao cliente informado")
    void returnExceptionWhenOrderDoesNotBelongToCustomer() {

        Customer orderCustomer = new Customer();
        orderCustomer.setId(1);

        Customer addressCustomer = new Customer();
        addressCustomer.setId(1);

        Order order = new Order();
        order.setCustomer(orderCustomer);
        order.setStatus(OrderEnum.AGUARDANDO_PAGAMENTO);

        Address address = new Address();
        address.setId(20);
        address.setCustomer(addressCustomer);

        // Cliente 2 tentando alterar pedido do cliente 1
        OrderUpdateAddressDTO dto =
                new OrderUpdateAddressDTO(
                        20,
                        2
                );

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(addressRepository.findById(20))
                .thenReturn(Optional.of(address));

        OrderOwnershipException exception =
                Assertions.assertThrows(
                        OrderOwnershipException.class,
                        () -> orderService.updateOrderAddress(
                                1L,
                                dto
                        )
                );

        Assertions.assertEquals(
                "Order não pertence ao cliente informado.",
                exception.getMessage()
        );

        verify(orderRepository, never())
                .save(any(Order.class));
    }


    @Test
    @DisplayName("Deve lançar OrderAddressOwnershipException quando o endereço pertencer a outro cliente")
    void returnExceptionWhenAddressBelongsToAnotherCustomer() {

        Customer orderCustomer = new Customer();
        orderCustomer.setId(1);

        Customer addressCustomer = new Customer();
        addressCustomer.setId(2);

        Order order = new Order();
        order.setCustomer(orderCustomer);
        order.setStatus(OrderEnum.AGUARDANDO_PAGAMENTO);

        Address address = new Address();
        address.setId(20);
        address.setCustomer(addressCustomer);

        OrderUpdateAddressDTO dto =
                new OrderUpdateAddressDTO(
                        20,
                        1
                );

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(addressRepository.findById(20))
                .thenReturn(Optional.of(address));

        OrderAddressOwnershipException exception =
                Assertions.assertThrows(
                        OrderAddressOwnershipException.class,
                        () -> orderService.updateOrderAddress(
                                1L,
                                dto
                        )
                );

        Assertions.assertEquals(
                "Endereço não pertence ao usuário do carrinho",
                exception.getMessage()
        );

        verify(orderRepository, never())
                .save(any(Order.class));
    }


    @Test
    @DisplayName("Deve lançar InvalidOrderStatusException quando o pedido não estiver com status CRIADO")
    void returnExceptionWhenOrderStatusDoesNotAllowAddressUpdate() {

        Customer customer = new Customer();
        customer.setId(1);

        Order order = new Order();
        order.setCustomer(customer);

        // Ajuste para algum status existente no seu enum
        // que seja diferente de CRIADO
        order.setStatus(OrderEnum.PAGO);

        Address address = new Address();
        address.setId(20);
        address.setCustomer(customer);

        OrderUpdateAddressDTO dto =
                new OrderUpdateAddressDTO(
                        20,
                        1
                );

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(addressRepository.findById(20))
                .thenReturn(Optional.of(address));

        InvalidOrderStatusException exception =
                Assertions.assertThrows(
                        InvalidOrderStatusException.class,
                        () -> orderService.updateOrderAddress(
                                1L,
                                dto
                        )
                );

        Assertions.assertEquals(
                "Endereço do pedido não pode ser alterado, pois está "
                        + OrderEnum.PAGO,
                exception.getMessage()
        );

        verify(orderRepository, never())
                .save(any(Order.class));
    }
}