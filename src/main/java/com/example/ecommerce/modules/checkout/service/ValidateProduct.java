package com.example.ecommerce.modules.checkout.service;

import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.product.exception.InsufficientStockException;
import com.example.ecommerce.modules.product.exception.ProductNotFoundException;
import com.example.ecommerce.modules.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ValidateProduct {
    public void validarEstoqueProduct(CartItem cartItem) {

        Product product = cartItem.getProduct();

        if (product == null) {
            throw new ProductNotFoundException(
                    "Produto do carrinho não encontrado"
            );
        }

        if (cartItem.getQuantidade() <= 0) {
            throw new InsufficientStockException(
                    "Quantidade inválida para o produto "
                            + product.getNome()
            );
        }

        int estoqueDisponivel =
                product.getQuantidadeEstoque()
                        - product.getQuantidadeReservada();

        if (cartItem.getQuantidade() > estoqueDisponivel) {
            throw new InsufficientStockException(
                    "Estoque insuficiente para "
                            + product.getNome()
                            + ". Disponível: "
                            + estoqueDisponivel
            );
        }
    }
}
