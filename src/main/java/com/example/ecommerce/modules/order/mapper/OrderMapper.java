package com.example.ecommerce.modules.order.mapper;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerResponseDTO;
import com.example.ecommerce.modules.order.dto.OrderItemResponseDTO;
import com.example.ecommerce.modules.order.dto.OrderResponseDTO;
import com.example.ecommerce.modules.order.model.Order;

import java.util.stream.Stream;

public class OrderMapper {
    public static OrderResponseDTO toOrderResponseDTO(Order order) {
        Stream<OrderItemResponseDTO> orderItem = order.getOrderItem().stream().map(orderItems -> new OrderItemResponseDTO(orderItems.getNomeProduto(),
                orderItems.getSkuProduto(),
                orderItems.getQuantidade(),
                orderItems.getPrecoUnitario(),
                orderItems.getSubTotal(),
                orderItems.getProduct().getId()));
        CustomerResponseDTO customer = new CustomerResponseDTO(
                order.getUsuario().getId(),
                order.getUsuario().getNomeCompleto(),
                order.getUsuario().getCpf(),
                order.getUsuario().getEmail(),
                order.getUsuario().getTelefone(),
                order.getUsuario().getStatus()
        );
        AddressListDTO address = new AddressListDTO(
                order.getAddress().getId(),
                order.getAddress().getNomeEndereco(),
                order.getAddress().getNomeDestinatario(),
                order.getAddress().getCep(),
                order.getAddress().getRua(),
                order.getAddress().getNumero(),
                order.getAddress().getComplemento(),
                order.getAddress().getBairro(),
                order.getAddress().getCidade(),
                order.getAddress().getEstado(),
                order.getAddress().getReferencia(),
                order.getAddress().getTipoEndereco(),
                order.getAddress().getEnderecoPrincipal(),
                order.getAddress().getDataCriacao(),
                order.getAddress().getDataAtualizacao()
        );
        return new OrderResponseDTO(order.getOrderId(), order.getValorTotal(),
                order.getStatus(), customer, address, orderItem);
    }
}
