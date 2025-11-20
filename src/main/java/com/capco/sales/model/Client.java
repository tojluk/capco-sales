package com.capco.sales.model;

import com.capco.sales.pricing.PricingStrategy;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base interface for all client types.
 * Clients can be either individual or professional, each with their own pricing strategy.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = IndividualClient.class, name = "INDIVIDUAL"),
    @JsonSubTypes.Type(value = ProfessionalClient.class, name = "PROFESSIONAL")
})
public sealed interface Client permits IndividualClient, ProfessionalClient {

    /**
     * @return the unique client identifier
     */
    String clientId();

    /**
     * @return the pricing strategy applicable to this client
     */
    PricingStrategy getPricingStrategy();
}
