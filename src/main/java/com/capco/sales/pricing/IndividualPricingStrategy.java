package com.capco.sales.pricing;

import com.capco.sales.model.ProductType;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Pricing strategy for individual clients.
 * High-end phone: €1500, Mid-range phone: €800, Laptop: €1200
 */
public record IndividualPricingStrategy() implements PricingStrategy {

    private static final Map<ProductType, BigDecimal> PRICES = Map.of(
        ProductType.HIGH_END_PHONE, new BigDecimal("1500"),
        ProductType.MID_RANGE_PHONE, new BigDecimal("800"),
        ProductType.LAPTOP, new BigDecimal("1200")
    );

    @Override
    public BigDecimal getPrice(ProductType productType) {
        return PRICES.get(productType);
    }
}
