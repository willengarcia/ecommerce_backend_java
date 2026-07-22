package com.example.ecommerce.modules.order.service;

import com.example.ecommerce.modules.address.exception.AddressNotFoundException;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.order.dto.OrderCreateDTO;
import com.example.ecommerce.modules.order.dto.OrderUpdateAddressDTO;
import com.example.ecommerce.modules.order.exception.*;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.model.OrderEnum;
import com.example.ecommerce.modules.order.respository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class OrderService{
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    public OrderService(OrderRepository orderRepository,  AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Order createOrder(OrderCreateDTO orderCreateDTO, Integer idCustomer) {
        if (idCustomer == null) {
            throw new InvalidOrderDataException("É necessário informar o id do Customer.");
        }
        if (orderCreateDTO == null) {
            throw new InvalidOrderDataException("Os dados do pedido não foram informados.");
        }
        if (orderCreateDTO.cart() == null) {
            throw new InvalidOrderDataException("O carrinho é obrigatório.");
        }
        if (orderCreateDTO.address() == null) {
            throw new InvalidOrderDataException("O endereço é obrigatório.");
        }
        if (orderCreateDTO.cart().getUsuario() == null) {
            throw new InvalidOrderDataException("O usuário do carrinho é obrigatório.");
        }

        if (orderCreateDTO.address().getId() == null) {
            throw new InvalidOrderDataException("O ID do endereço é obrigatório.");
        }
        Order order = getOrder(orderCreateDTO);
        return orderRepository.save(order);
    }

    private static Order getOrder(OrderCreateDTO orderCreateDTO) {
        Order order = new Order();
        order.setStatus(OrderEnum.AGUARDANDO_PAGAMENTO);
        order.setUsuario(orderCreateDTO.cart().getUsuario());
        order.setAddress(orderCreateDTO.address());
        order.setIdAddress(orderCreateDTO.address().getId());
        order.setNomeEndereco(orderCreateDTO.address().getNomeEndereco());
        order.setNomeDestinatario(orderCreateDTO.address().getNomeDestinatario());
        order.setCep(orderCreateDTO.address().getCep());
        order.setRua(orderCreateDTO.address().getRua());
        order.setNumero(orderCreateDTO.address().getNumero());
        order.setComplemento(orderCreateDTO.address().getComplemento());
        order.setBairro(orderCreateDTO.address().getBairro());
        order.setEstado(orderCreateDTO.address().getEstado());
        order.setCidade(orderCreateDTO.address().getCidade());
        order.setReferencia(orderCreateDTO.address().getReferencia());
        order.setTipoEndereco(orderCreateDTO.address().getTipoEndereco());
        order.setEnderecoPrincipal(orderCreateDTO.address().getEnderecoPrincipal());
        order.setValorTotal(orderCreateDTO.cart().getValorTotal());
        return order;
    }

    public List<Order> getOrderById() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> order = orderRepository.findByUsuarioId(userId);
        return order.stream().toList();
    }

    @Transactional
    public Order updateOrderAddress(Long orderId, OrderUpdateAddressDTO orderUpdateAddressDTO) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Address address = addressRepository.findById(orderUpdateAddressDTO.addressId()).orElseThrow(
                ()-> new AddressNotFoundException("Endereço não encontrado!")
        );
        if (orderUpdateAddressDTO.customerId() == null || !order.getUsuario().getId().equals(orderUpdateAddressDTO.customerId())) {
            throw new OrderOwnershipException("Order não pertence ao cliente informado.");
        }
        if (!Objects.equals(order.getUsuario().getId(), address.getUsuario().getId())) {
            throw new OrderAddressOwnershipException("Endereço não pertence ao usuário do carrinho");
        }
        if (!order.getStatus().equals(OrderEnum.CRIADO)) {
            throw new InvalidOrderStatusException("Endereço do pedido não pode ser alterado, pois está "+order.getStatus());
        }
        order.setAddress(address);
        order.setIdAddress(address.getId());
        order.setNomeEndereco(address.getNomeEndereco());
        order.setNomeDestinatario(address.getNomeDestinatario());
        order.setCep(address.getCep());
        order.setRua(address.getRua());
        order.setNumero(address.getNumero());
        order.setComplemento(address.getComplemento());
        order.setBairro(address.getBairro());
        order.setEstado(address.getEstado());
        order.setCidade(address.getCidade());
        order.setReferencia(address.getReferencia());
        order.setTipoEndereco(address.getTipoEndereco());
        order.setEnderecoPrincipal(address.getEnderecoPrincipal());
        return orderRepository.save(order);
    }
}
