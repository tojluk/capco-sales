package com.capco.sales.model;

import com.capco.sales.pricing.HighRevenueProfessionalPricingStrategy;
import com.capco.sales.pricing.LowRevenueProfessionalPricingStrategy;
import com.capco.sales.pricing.PricingStrategy;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * Represents a professional client (business).
 * Professional clients receive different pricing based on their annual revenue.
 * Companies with revenue greater than €10 million get better pricing.
 */
@JsonTypeName("PROFESSIONAL")
public record ProfessionalClient(
        @NotBlank(message = "Client ID is required")
        String clientId,

        @NotBlank(message = "Company name is required")
        String companyName,

        String vatNumber,

        @NotBlank(message = "Registration number is required")
        String registrationNumber,

        @NotNull(message = "Annual revenue is required")
        @PositiveOrZero(message = "Annual revenue must be positive or zero")
        BigDecimal annualRevenue
) implements Client {

    private static final BigDecimal HIGH_REVENUE_THRESHOLD = new BigDecimal("10000000");

    @Override
    public PricingStrategy getPricingStrategy() {
        if (annualRevenue.compareTo(HIGH_REVENUE_THRESHOLD) > 0) {
            return new HighRevenueProfessionalPricingStrategy();
        }
        return new LowRevenueProfessionalPricingStrategy();
    }
}
