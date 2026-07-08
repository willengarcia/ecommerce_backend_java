package com.example.ecommerce.modules.order.service;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.order.dto.OrderCreateDTO;
import com.example.ecommerce.modules.order.dto.OrderUpdateAddressDTO;
import com.example.ecommerce.modules.order.exception.OrderException;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.model.OrderEnum;
import com.example.ecommerce.modules.order.respository.OrderRepository;
import org.springframework.stereotype.Service;
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

    public Order createOrder(OrderCreateDTO orderCreateDTO, Integer idCustomer) {
        if (orderCreateDTO == null) {
            throw new OrderException("Os dados do pedido não foram informados.");
        }
        if (orderCreateDTO.cart() == null) {
            throw new OrderException("O carrinho é obrigatório.");
        }
        if (orderCreateDTO.address() == null) {
            throw new OrderException("O endereço é obrigatório.");
        }
        if (orderCreateDTO.cart().getUsuario() == null) {
            throw new OrderException("O usuário do carrinho é obrigatório.");
        }

        if (orderCreateDTO.address().getId() == null) {
            throw new OrderException("O ID do endereço é obrigatório.");
        }
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
        return orderRepository.save(order);
    }

    public List<Order> getOrderById() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> order = orderRepository.findByUsuarioId(userId);
        return order.stream().toList();
    }

    public Order updateOrderAddress(Long orderId, OrderUpdateAddressDTO addressId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Address address = addressRepository.findById(addressId.addressId()).orElseThrow(
                ()-> new OrderException("Endereço não encontrado!")
        );
        if (!Objects.equals(order.getUsuario().getId(), address.getUsuario().getId())) {
            throw new OrderException("Endereço não pertence ao usuário do carrinho");
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
