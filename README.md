# E-commerce Backend Java

Backend de um sistema de e-commerce desenvolvido em Java com Spring Boot.

## Objetivo

Construir uma API para gerenciamento de clientes, produtos, categorias, carrinho, pedidos, pagamentos e faturamento.

## Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Git/GitHub

## Módulos previstos

- Customer
- Address
- Category
- Product
- Cart
- Order
- Payment
- Stock
- Review
- Coupon

## Estrutura do projeto

```text
com.example.ecommerce
|
|__ config
|
|__ exceptions
|   |__ GlobalExceptionHandler
|   |__ ResponseError
|
|__ externalServices
|   |__ payment
|   |__ storage
|
|__ modules
|   |
|   |__ address
|   |   |__ controller
|   |   |__ dto
|   |   |__ exception
|   |   |__ mapper
|   |   |__ model
|   |   |__ repository
|   |   |__ service
|   |       |__ AddressService
|   |
|   |__ cart
|   |
|   |__ category
|   |
|   |__ checkout
|   |
|   |__ customers
|   |
|   |__ importation
|   |   |__ category
|   |   |__ product
|   |       |__ controller
|   |       |   |__ ImportProductController
|   |       |__ dto
|   |       |__ exception
|   |       |__ mapper
|   |       |__ service
|   |
|   |__ order
|   |
|   |__ product
|
|__ EcommerceApplication
