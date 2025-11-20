package com.capco.sales.dto;

import com.capco.sales.model.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a single item in a shopping cart.
 *
 * @param productType the type of product
 * @param quantity the quantity of this product (must be at least 1)
 */
@Schema(description = "Shopping cart item with product type and quantity")
public record CartItemDto(

        @Schema(description = "Type of product", example = "HIGH_END_PHONE", required = true)
        @NotNull(message = "Product type is required")
        ProductType productType,

        @Schema(description = "Quantity of this product", example = "2", minimum = "1", required = true)
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) {
}
