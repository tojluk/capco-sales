package com.capco.sales.pricing;

import com.capco.sales.model.ProductType;

import java.math.BigDecimal;

/**
 * Strategy pattern interface for calculating product prices.
 * Different implementations provide pricing based on client type and characteristics.
 */
public sealed interface PricingStrategy permits IndividualPricingStrategy,
                                                HighRevenueProfessionalPricingStrategy,
                                                LowRevenueProfessionalPricingStrategy {

    /**
     * Gets the price for a specific product type according to this pricing strategy.
     *
     * @param productType the type of product to price
     * @return the price for the product
     */
    BigDecimal getPrice(ProductType productType);
}
