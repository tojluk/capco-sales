package com.capco.sales.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO containing the calculated shopping cart total.
 *
 * @param total the total cost of all items in the cart
 * @param itemDetails detailed breakdown of each item's pricing
 */
@Schema(description = "Shopping cart calculation result with total and item details")
public record CartTotalResponse(
        @Schema(description = "Total cost of all items in the cart", example = "4200")
        BigDecimal total,

        @Schema(description = "Detailed breakdown of pricing for each item")
        List<ItemDetail> itemDetails
) {
}
