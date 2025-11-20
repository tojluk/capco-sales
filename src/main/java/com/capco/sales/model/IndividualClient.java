package com.capco.sales.model;

import com.capco.sales.pricing.IndividualPricingStrategy;
import com.capco.sales.pricing.PricingStrategy;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents an individual client.
 * Individual clients are identified by their name and receive standard pricing.
 */
@JsonTypeName("INDIVIDUAL")
public record IndividualClient(
    @NotBlank(message = "Client ID is required")
    String clientId,

    @NotBlank(message = "First name is required")
    String firstName,

    @NotBlank(message = "Last name is required")
    String lastName
) implements Client {

    @Override
    public PricingStrategy getPricingStrategy() {
        return new IndividualPricingStrategy();
    }

}
