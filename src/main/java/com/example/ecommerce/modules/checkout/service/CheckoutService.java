package com.example.ecommerce.modules.checkout.service;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.checkout.dto.CheckoutRequestDTO;
import com.example.ecommerce.modules.order.dto.OrderCreateDTO;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.model.OrderItem;
import com.example.ecommerce.modules.order.respository.OrderItemRepository;
import com.example.ecommerce.modules.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;

    public CheckoutService(CartRepository cartRepository, AddressRepository addressRepository, OrderService orderService, OrderItemRepository orderItemRepository) {
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.orderService = orderService;
        this.orderItemRepository = orderItemRepository;
    }

    public Cart findCartId(Integer cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }

    public Address findAddressId(Integer addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }

    public Order finalizarCompra(CheckoutRequestDTO dto) {
        Cart cart = findCartId(Math.toIntExact(dto.cartId()));
        Address address = findAddressId(dto.addressId());
        if (!Objects.equals(cart.getUsuario().getId(), address.getUsuario().getId())) {
            throw new RuntimeException("Endereço não pertence ao usuário do carrinho");
        }
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Carrinho vazio");
        }
        Order orderCriado = orderService.createOrder(new OrderCreateDTO(cart, address, "PIX"));
        List<OrderItem> itemsCriados = create(orderCriado, cart);
        orderCriado.setOrderItem(itemsCriados);
        return orderCriado;
    }

    public List<OrderItem> create(Order order, Cart cart) {
        List<OrderItem> orderItems = new ArrayList<>();
        List<CartItem> cartItems = cart.getItems();
        cartItems.forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setSubTotal(cartItem.getSubtotal());
            orderItem.setNomeProduto(cartItem.getProduct().getNome());
            orderItem.setDataAtualizacao(LocalDateTime.now());
            orderItem.setDataCriacao(LocalDateTime.now());
            orderItem.setQuantidade(cartItem.getQuantidade());
            orderItem.setSkuProduto(cartItem.getProduct().getSku());
            orderItem.setPrecoUnitario(cartItem.getPrecoUnitario());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        });
        return orderItems;
    }

}
