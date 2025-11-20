package com.capco.sales.dto;

import com.capco.sales.model.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Detailed pricing information for a single cart item.
 *
 * @param productType the type of product
 * @param quantity the quantity purchased
 * @param unitPrice the price per unit
 * @param totalPrice the total price (unitPrice × quantity)
 */
@Schema(description = "Pricing details for a single cart item")
public record ItemDetail(
        @Schema(description = "Type of product", example = "HIGH_END_PHONE")
        ProductType productType,

        @Schema(description = "Quantity purchased", example = "2")
        Integer quantity,

        @Schema(description = "Price per unit", example = "1500")
        BigDecimal unitPrice,

        @Schema(description = "Total price for this item (unitPrice × quantity)", example = "3000")
        BigDecimal totalPrice
) {
}
