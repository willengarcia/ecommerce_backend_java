package com.example.ecommerce.modules.checkout.service;

import com.example.ecommerce.modules.address.exception.AddressNotFoundException;
import com.example.ecommerce.modules.address.exception.AddressOwnershipException;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.cart.exception.CartAlreadyAbandonedException;
import com.example.ecommerce.modules.cart.exception.CartNotFoundException;
import com.example.ecommerce.modules.cart.exception.CartOwnershipException;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartEnum;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.repository.CartRepository;
import com.example.ecommerce.modules.checkout.dto.CheckoutRequestDTO;
import com.example.ecommerce.modules.checkout.exception.EmptyCartException;
import com.example.ecommerce.modules.order.dto.OrderCreateDTO;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.model.OrderItem;
import com.example.ecommerce.modules.order.repository.OrderItemRepository;
import com.example.ecommerce.modules.order.service.OrderService;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ValidateProduct validateProduct;
    private final ProductRepository productRepository;

    public CheckoutService(CartRepository cartRepository, AddressRepository addressRepository, OrderService orderService, OrderItemRepository orderItemRepository, ValidateProduct validateProduct, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.orderService = orderService;
        this.orderItemRepository = orderItemRepository;
        this.validateProduct = validateProduct;
        this.productRepository = productRepository;
    }

    public Cart findCartId(Integer cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Carrinho não encontrado"));
    }

    public Address findAddressId(Integer addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Endererço não encontrado"));
    }

    @Transactional
    public Order completePurchase(CheckoutRequestDTO dto, Integer idCustomer) {

        Cart cart = findCartId(
                Math.toIntExact(dto.cartId())
        );

        Address address = findAddressId(
                dto.addressId()
        );

        if (!cart.getCustomer().getId().equals(idCustomer)) {
            throw new CartOwnershipException("Esse usuário não pertence a esse Carrinho.");
        }

        if (!Objects.equals(cart.getCustomer().getId(),address.getCustomer().getId())) {
            throw new AddressOwnershipException("Endereço não pertence ao usuário do carrinho");
        }

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Carrinho vazio");
        }

        if (!cart.getStatus().equals(CartEnum.ATIVO)) {
            throw new CartAlreadyAbandonedException("Carrinho com status diferente de ATIVO");
        }

        // valida todo estoque
        for (CartItem cartItem : cart.getItems()) {
            validateProduct.validarEstoqueProduct(cartItem);
        }

        // reserva estoque
        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            product.setQuantidadeReservada(
                    product.getQuantidadeReservada()
                            + cartItem.getQuantidade()
            );

            productRepository.save(product);
        }

        // cria pedido
        Order orderCriado = orderService.createOrder( new OrderCreateDTO(cart, address, "PIX"));

        // cria items do pedido
        List<OrderItem> itemsCriados = createOrderItem(orderCriado, cart);

        orderCriado.setOrderItem(itemsCriados);

        orderCriado.calcularValorTotal();

        // converte o carrinho
        cart.setStatus(CartEnum.CONVERTIDO);

        cartRepository.save(cart);

        return orderCriado;
    }

    public List<OrderItem> createOrderItem(Order order, Cart cart) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setNomeProduto(
                    cartItem.getProduct().getNome()
            );

            orderItem.setQuantidade(
                    cartItem.getQuantidade()
            );

            orderItem.setSkuProduto(
                    cartItem.getProduct().getSku()
            );

            orderItem.setPrecoUnitario(
                    cartItem.getProduct().getPrecoVenda()
            );

            orderItem.setProduct(
                    cartItem.getProduct()
            );

            orderItem.setOrder(order);

            orderItem.setDataCriacao(
                    LocalDateTime.now()
            );

            orderItem.setDataAtualizacao(
                    LocalDateTime.now()
            );

            // OrderItem calcula seu próprio subtotal
            orderItem.calcularSubtotal();

            orderItemRepository.save(orderItem);

            orderItems.add(orderItem);
        }

        return orderItems;
    }


}
