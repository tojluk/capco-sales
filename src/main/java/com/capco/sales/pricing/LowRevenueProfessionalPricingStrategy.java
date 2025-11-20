package com.capco.sales.pricing;

import com.capco.sales.model.ProductType;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Pricing strategy for professional clients with annual revenue below €10 million.
 * High-end phone: €1150, Mid-range phone: €600, Laptop: €1000
 */
public record LowRevenueProfessionalPricingStrategy() implements PricingStrategy {

    private static final Map<ProductType, BigDecimal> PRICES = Map.of(
        ProductType.HIGH_END_PHONE, new BigDecimal("1150"),
        ProductType.MID_RANGE_PHONE, new BigDecimal("600"),
        ProductType.LAPTOP, new BigDecimal("1000")
    );

    @Override
    public BigDecimal getPrice(ProductType productType) {
        return PRICES.get(productType);
    }
}
