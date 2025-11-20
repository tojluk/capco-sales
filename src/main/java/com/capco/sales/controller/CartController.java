package com.capco.sales.controller;

import com.capco.sales.dto.CartTotalResponse;
import com.capco.sales.dto.ShoppingCartRequest;
import com.capco.sales.exception.ErrorResponse;
import com.capco.sales.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for shopping cart operations.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart", description = "Shopping cart calculation APIs")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "500",
                description = "Unexpected service error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request - validation failed",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        )
})
public class CartController {

    private final CartService cartService;

    @Operation(
            summary = "Calculate shopping cart total",
            description = """
                    Calculates the total cost of a shopping cart based on client type and product quantities.

                    **Pricing rules:**
                    - **Individual clients:** High-end phone €1500, Mid-range phone €800, Laptop €1200
                    - **Professional clients (revenue > €10M):** High-end phone €1000, Mid-range phone €550, Laptop €900
                    - **Professional clients (revenue ≤ €10M):** High-end phone €1150, Mid-range phone €600, Laptop €1000
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully calculated cart total with detailed breakdown",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CartTotalResponse.class)
            )
    )
    @PostMapping("/cart/calculate")
    public CartTotalResponse calculateCartTotal(@Valid @RequestBody ShoppingCartRequest request) {
        return cartService.calculateTotal(request);
    }

}
