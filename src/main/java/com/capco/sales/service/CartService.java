package com.capco.sales.service;

import com.capco.sales.dto.CartItemDto;
import com.capco.sales.dto.CartTotalResponse;
import com.capco.sales.dto.ItemDetail;
import com.capco.sales.dto.ShoppingCartRequest;
import com.capco.sales.pricing.PricingStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service responsible for shopping cart calculations.
 * Applies appropriate pricing strategies based on client type and calculates cart totals.
 */
@Service
public class CartService {

    /**
     * Calculates the total cost of a shopping cart for a given client.
     * The pricing is determined by the client's pricing strategy (individual or professional).
     *
     * @param request the shopping cart request containing client information and cart items
     * @return the cart total response with detailed breakdown of prices per item
     */
    public CartTotalResponse calculateTotal(ShoppingCartRequest request) {
        PricingStrategy strategy = request.client().getPricingStrategy();

        List<ItemDetail> itemDetails = request.items()
                                              .stream()
                                              .map(item -> calculateItemDetail(item, strategy))
                                              .toList();

        BigDecimal total = itemDetails.stream()
                                      .map(ItemDetail::totalPrice)
                                      .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartTotalResponse(total, itemDetails);
    }

    /**
     * Calculates the detail for a single cart item.
     *
     * @param item the cart item to calculate
     * @param strategy the pricing strategy to apply
     * @return the item detail with unit price and total price
     */
    private ItemDetail calculateItemDetail(CartItemDto item, PricingStrategy strategy) {
        BigDecimal unitPrice = strategy.getPrice(item.productType());
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(item.quantity()));

        return new ItemDetail(
                item.productType(),
                item.quantity(),
                unitPrice,
                totalPrice
        );
    }
}
