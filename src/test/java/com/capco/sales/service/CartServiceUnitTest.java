package com.capco.sales.service;

import com.capco.sales.TestDataFactory;
import com.capco.sales.dto.CartItemDto;
import com.capco.sales.dto.CartTotalResponse;
import com.capco.sales.dto.ItemDetail;
import com.capco.sales.dto.ShoppingCartRequest;
import com.capco.sales.model.IndividualClient;
import com.capco.sales.model.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.capco.sales.TestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class CartServiceUnitTest {

    private CartService cartService;

    @BeforeEach
    void setup() {
        cartService = new CartService();
    }

    @Test
    void shouldCalculateTotal_whenCalculateTotal_givenIndividualClientWithMultipleItems() {
        // given
        ShoppingCartRequest givenRequest = TestDataFactory.createShoppingCartRequestIndividual();
        CartTotalResponse expected = TestDataFactory.createExpectedResponseIndividual();

        // when
        CartTotalResponse actual = cartService.calculateTotal(givenRequest);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldCalculateTotal_whenCalculateTotal_givenProfessionalHighRevenueClient() {
        // given
        ShoppingCartRequest givenRequest = TestDataFactory.createShoppingCartRequestProfessionalHighRevenue();
        CartTotalResponse expected = TestDataFactory.createExpectedResponseProfessionalHighRevenue();

        // when
        CartTotalResponse actual = cartService.calculateTotal(givenRequest);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldCalculateTotal_whenCalculateTotal_givenProfessionalLowRevenueClient() {
        // given
        ShoppingCartRequest givenRequest = TestDataFactory.createShoppingCartRequestProfessionalLowRevenue();
        CartTotalResponse expected = TestDataFactory.createExpectedResponseProfessionalLowRevenue();

        // when
        CartTotalResponse actual = cartService.calculateTotal(givenRequest);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldCalculateTotal_whenCalculateTotal_givenSingleItem() {
        // given
        IndividualClient givenClient = TestDataFactory.createIndividualClient();
        CartItemDto givenItem = new CartItemDto(ProductType.LAPTOP, 1);
        ShoppingCartRequest givenRequest = new ShoppingCartRequest(givenClient, List.of(givenItem));

        BigDecimal expectedTotal = PRICE_INDIVIDUAL_LAPTOP;
        ItemDetail expectedItemDetail = new ItemDetail(
                ProductType.LAPTOP,
                1,
                PRICE_INDIVIDUAL_LAPTOP,
                PRICE_INDIVIDUAL_LAPTOP
        );
        CartTotalResponse expected = new CartTotalResponse(expectedTotal, List.of(expectedItemDetail));

        // when
        CartTotalResponse actual = cartService.calculateTotal(givenRequest);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldCalculateTotal_whenCalculateTotal_givenMultipleQuantities() {
        // given
        IndividualClient givenClient = TestDataFactory.createIndividualClient();
        CartItemDto givenItem = new CartItemDto(ProductType.HIGH_END_PHONE, 5);
        ShoppingCartRequest givenRequest = new ShoppingCartRequest(givenClient, List.of(givenItem));

        BigDecimal expectedUnitPrice = PRICE_INDIVIDUAL_HIGH_END;
        BigDecimal expectedTotal = PRICE_INDIVIDUAL_HIGH_END.multiply(BigDecimal.valueOf(5));
        ItemDetail expectedItemDetail = new ItemDetail(
                ProductType.HIGH_END_PHONE,
                5,
                expectedUnitPrice,
                expectedTotal
        );
        CartTotalResponse expected = new CartTotalResponse(expectedTotal, List.of(expectedItemDetail));

        // when
        CartTotalResponse actual = cartService.calculateTotal(givenRequest);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
