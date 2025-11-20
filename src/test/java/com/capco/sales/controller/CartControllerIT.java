package com.capco.sales.controller;

import com.capco.sales.TestDataFactory;
import com.capco.sales.dto.CartTotalResponse;
import com.capco.sales.dto.ShoppingCartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerIT {

    private static final String API_ENDPOINT = "/api/v1/cart/calculate";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Helper methods for common expectations

    private ResultActions expectSuccessResponse(ResultActions resultActions, CartTotalResponse expectedResponse) throws Exception {
        return resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    private ResultActions expectBadRequestWithValidationFailure(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    private ResultActions expectBadRequest(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void shouldReturnCartTotal_whenCalculateCartTotal_givenIndividualClientWithMultipleItems() throws Exception {
        // given
        ShoppingCartRequest givenRequest = TestDataFactory.createShoppingCartRequestIndividual();
        CartTotalResponse expectedResponse = TestDataFactory.createExpectedResponseIndividual();

        // when & then
        expectSuccessResponse(
                mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest))),
                expectedResponse
        );
    }

    @Test
    void shouldReturnCartTotal_whenCalculateCartTotal_givenProfessionalHighRevenueClientWithSingleItem() throws Exception {
        // given
        ShoppingCartRequest givenRequest = TestDataFactory.createShoppingCartRequestProfessionalHighRevenue();
        CartTotalResponse expectedResponse = TestDataFactory.createExpectedResponseProfessionalHighRevenue();

        // when & then
        expectSuccessResponse(
                mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest))),
                expectedResponse
        );
    }

    @Test
    void shouldReturnCartTotal_whenCalculateCartTotal_givenProfessionalLowRevenueClientWithSingleItem() throws Exception {
        // given
        ShoppingCartRequest givenRequest = TestDataFactory.createShoppingCartRequestProfessionalLowRevenue();
        CartTotalResponse expectedResponse = TestDataFactory.createExpectedResponseProfessionalLowRevenue();

        // when & then
        expectSuccessResponse(
                mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest))),
                expectedResponse
        );
    }

    @Test
    void shouldReturnBadRequest_whenCalculateCartTotal_givenInvalidFields() throws Exception {
        // given - request with multiple validation errors
        String givenInvalidRequest = TestDataFactory.createInvalidRequestWithAllMissingFields();

        // when & then
        expectBadRequestWithValidationFailure(
                mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenInvalidRequest))
        )
                .andExpect(jsonPath("$.errors.client").exists())
                .andExpect(jsonPath("$.errors['items[0].productType']").exists())
                .andExpect(jsonPath("$.errors['items[0].quantity']").exists());
    }

    @Test
    void shouldReturnBadRequest_whenCalculateCartTotal_givenInvalidClientType() throws Exception {
        // given
        String givenInvalidRequest = TestDataFactory.createInvalidRequestWithInvalidClientType();

        // when & then
        expectBadRequest(
                mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenInvalidRequest))
        );
    }

    @Test
    void shouldReturnBadRequest_whenCalculateCartTotal_givenNullItems() throws Exception {
        // given
        String givenInvalidRequest = TestDataFactory.createInvalidRequestWithNullItems();

        // when & then
        expectBadRequestWithValidationFailure(
                mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenInvalidRequest))
        )
                .andExpect(jsonPath("$.errors.items").exists());
    }

    @Test
    void shouldReturnBadRequest_whenCalculateCartTotal_givenEmptyItems() throws Exception {
        // given
        String givenInvalidRequest = TestDataFactory.createInvalidRequestWithEmptyItems();

        // when & then
        expectBadRequestWithValidationFailure(
                mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenInvalidRequest))
        )
                .andExpect(jsonPath("$.errors.items").exists());
    }

    @Test
    void shouldReturnBadRequest_whenCalculateCartTotal_givenInvalidProfessionalClientFields() throws Exception {
        // given - professional client with missing required fields and invalid revenue
        String givenInvalidRequest = TestDataFactory.createInvalidProfessionalClientRequest();

        // when & then
        expectBadRequestWithValidationFailure(
                mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenInvalidRequest))
        )
                .andExpect(jsonPath("$.errors['client.clientId']").exists())
                .andExpect(jsonPath("$.errors['client.companyName']").exists())
                .andExpect(jsonPath("$.errors['client.registrationNumber']").exists())
                .andExpect(jsonPath("$.errors['client.annualRevenue']").exists());
    }

    @Test
    void shouldReturnBadRequest_whenCalculateCartTotal_givenInvalidIndividualClientFields() throws Exception {
        // given - individual client with empty/blank required fields
        String givenInvalidRequest = TestDataFactory.createInvalidIndividualClientRequest();

        // when & then
        expectBadRequestWithValidationFailure(
                mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenInvalidRequest))
        )
                .andExpect(jsonPath("$.errors['client.clientId']").exists())
                .andExpect(jsonPath("$.errors['client.firstName']").exists())
                .andExpect(jsonPath("$.errors['client.lastName']").exists());
    }
}
