package com.capco.sales.dto;

import com.capco.sales.model.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Request DTO for calculating shopping cart total.
 *
 * @param client the client making the purchase
 * @param items the list of items in the shopping cart
 */
@Schema(
        description = "Shopping cart request with client information and items",
        example = """
                {
                  "client": {
                    "type": "INDIVIDUAL",
                    "clientId": "IND001",
                    "firstName": "John",
                    "lastName": "Doe"
                  },
                  "items": [
                    {
                      "productType": "HIGH_END_PHONE",
                      "quantity": 2
                    },
                    {
                      "productType": "LAPTOP",
                      "quantity": 1
                    }
                  ]
                }
                """
)
public record ShoppingCartRequest(
        @Schema(description = "Client making the purchase", required = true)
        @NotNull(message = "Client is required")
        @Valid
        Client client,

        @Schema(description = "List of items in the cart", required = true)
        @NotEmpty(message = "Cart items cannot be empty")
        List<@Valid CartItemDto> items
) {
}
