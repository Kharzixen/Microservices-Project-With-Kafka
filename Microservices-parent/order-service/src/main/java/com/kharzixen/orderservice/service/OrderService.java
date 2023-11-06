package com.kharzixen.orderservice.service;

import com.kharzixen.orderservice.Dto.CartDto;
import com.kharzixen.orderservice.Dto.CartItemDto;
import com.kharzixen.orderservice.Dto.InventoryDto;
import com.kharzixen.orderservice.client.CartServiceClient;
import com.kharzixen.orderservice.repository.OrderItemRepository;
import com.kharzixen.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
@Log
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WebClient.Builder webClientBuilder;
    private final CartServiceClient cartServiceClient;

    @Transactional
    public String createOrder(String cartId) {

        //catch errors, http status etc.

        //        CartDto cart = webClientBuilder.build().get()
        //                .uri("http://cart-service/api/carts/", uriBuilder -> uriBuilder.path("/{id}").build(cartId))
        //                .retrieve()
        //                .bodyToMono(CartDto.class)
        //                .block();

        CartDto cart = cartServiceClient.getCartById(cartId);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        if (cart.getItemList() == null || cart.getItemList().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        boolean areAllProductsInStock = true;

        for (CartItemDto cartItem : cart.getItemList()) {
            //catch errors, http status etc
            InventoryDto inventory = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("productId", cartItem.getProductId()).build())
                    .retrieve()
                    .bodyToMono(InventoryDto.class)
                    .block();

            if (inventory == null) {
                throw new RuntimeException("Inventory not found");
            }

            if (inventory.getQuantity() < cartItem.getQuantity()) {
                areAllProductsInStock = false;
            }
        }

        if (areAllProductsInStock) {
            log.severe("ORDER PLACED AS PENDING");
            return "ORDER PLACED AS PENDING";
        } else {
            log.severe("Cannot place order");
            return "Cannot place order";
        }


        //place order as "pending"

        //fire the "do reservation event"
    }
}
