package com.capco.sales.pricing;

import com.capco.sales.model.ProductType;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Pricing strategy for professional clients with annual revenue greater than €10 million.
 * High-end phone: €1000, Mid-range phone: €550, Laptop: €900
 */
public record HighRevenueProfessionalPricingStrategy() implements PricingStrategy {

    private static final Map<ProductType, BigDecimal> PRICES = Map.of(
        ProductType.HIGH_END_PHONE, new BigDecimal("1000"),
        ProductType.MID_RANGE_PHONE, new BigDecimal("550"),
        ProductType.LAPTOP, new BigDecimal("900")
    );

    @Override
    public BigDecimal getPrice(ProductType productType) {
        return PRICES.get(productType);
    }
}
