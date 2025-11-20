package com.capco.sales;

import com.capco.sales.dto.CartItemDto;
import com.capco.sales.dto.CartTotalResponse;
import com.capco.sales.dto.ItemDetail;
import com.capco.sales.dto.ShoppingCartRequest;
import com.capco.sales.model.IndividualClient;
import com.capco.sales.model.ProfessionalClient;
import com.capco.sales.model.ProductType;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;

/**
 * Test data factory for creating test objects using the Object Mother pattern.
 * Provides reusable test data creation methods for unit and integration tests.
 */
@UtilityClass
public final class TestDataFactory {

    // Client constants
    public static final String CLIENT_ID_INDIVIDUAL = "IND001";
    public static final String CLIENT_FIRST_NAME = "John";
    public static final String CLIENT_LAST_NAME = "Doe";
    public static final String CLIENT_ID_PROFESSIONAL_HIGH = "PRO001";
    public static final String COMPANY_NAME_HIGH = "Tech Corp";
    public static final String VAT_NUMBER = "FR123456789";
    public static final String REGISTRATION_NUMBER_HIGH = "REG123";
    public static final BigDecimal ANNUAL_REVENUE_HIGH = new BigDecimal("15000000");
    public static final String CLIENT_ID_PROFESSIONAL_LOW = "PRO002";
    public static final String COMPANY_NAME_LOW = "Small Business Ltd";
    public static final String REGISTRATION_NUMBER_LOW = "REG456";
    public static final BigDecimal ANNUAL_REVENUE_LOW = new BigDecimal("5000000");

    // Price constants
    public static final BigDecimal PRICE_INDIVIDUAL_HIGH_END = new BigDecimal("1500");
    public static final BigDecimal PRICE_INDIVIDUAL_LAPTOP = new BigDecimal("1200");
    public static final BigDecimal PRICE_PROFESSIONAL_HIGH_MID_RANGE = new BigDecimal("550");
    public static final BigDecimal PRICE_PROFESSIONAL_LOW_LAPTOP = new BigDecimal("1000");


    // Client factory methods
    public static IndividualClient createIndividualClient() {
        return new IndividualClient(
                CLIENT_ID_INDIVIDUAL,
                CLIENT_FIRST_NAME,
                CLIENT_LAST_NAME
        );
    }

    public static ProfessionalClient createProfessionalClientHighRevenue() {
        return new ProfessionalClient(
                CLIENT_ID_PROFESSIONAL_HIGH,
                COMPANY_NAME_HIGH,
                VAT_NUMBER,
                REGISTRATION_NUMBER_HIGH,
                ANNUAL_REVENUE_HIGH
        );
    }

    public static ProfessionalClient createProfessionalClientLowRevenue() {
        return new ProfessionalClient(
                CLIENT_ID_PROFESSIONAL_LOW,
                COMPANY_NAME_LOW,
                null,
                REGISTRATION_NUMBER_LOW,
                ANNUAL_REVENUE_LOW
        );
    }

    // Shopping cart request factory methods
    public static ShoppingCartRequest createShoppingCartRequestIndividual() {
        IndividualClient client = createIndividualClient();
        List<CartItemDto> items = List.of(
                new CartItemDto(ProductType.HIGH_END_PHONE, 2),
                new CartItemDto(ProductType.LAPTOP, 1)
        );
        return new ShoppingCartRequest(client, items);
    }

    public static ShoppingCartRequest createShoppingCartRequestProfessionalHighRevenue() {
        ProfessionalClient client = createProfessionalClientHighRevenue();
        List<CartItemDto> items = List.of(
                new CartItemDto(ProductType.MID_RANGE_PHONE, 10)
        );
        return new ShoppingCartRequest(client, items);
    }

    public static ShoppingCartRequest createShoppingCartRequestProfessionalLowRevenue() {
        ProfessionalClient client = createProfessionalClientLowRevenue();
        List<CartItemDto> items = List.of(
                new CartItemDto(ProductType.LAPTOP, 3)
        );
        return new ShoppingCartRequest(client, items);
    }

    // Expected success responses factory methods
    public static CartTotalResponse createExpectedResponseIndividual() {
        BigDecimal expectedTotal = new BigDecimal("4200"); // (1500 * 2) + (1200 * 1)
        List<ItemDetail> expectedItems = List.of(
                new ItemDetail(ProductType.HIGH_END_PHONE, 2, PRICE_INDIVIDUAL_HIGH_END, new BigDecimal("3000")),
                new ItemDetail(ProductType.LAPTOP, 1, PRICE_INDIVIDUAL_LAPTOP, PRICE_INDIVIDUAL_LAPTOP)
        );
        return new CartTotalResponse(expectedTotal, expectedItems);
    }

    public static CartTotalResponse createExpectedResponseProfessionalHighRevenue() {
        BigDecimal expectedTotal = new BigDecimal("5500"); // 550 * 10
        List<ItemDetail> expectedItems = List.of(
                new ItemDetail(ProductType.MID_RANGE_PHONE, 10, PRICE_PROFESSIONAL_HIGH_MID_RANGE, expectedTotal)
        );
        return new CartTotalResponse(expectedTotal, expectedItems);
    }

    public static CartTotalResponse createExpectedResponseProfessionalLowRevenue() {
        BigDecimal expectedTotal = new BigDecimal("3000"); // 1000 * 3
        List<ItemDetail> expectedItems = List.of(
                new ItemDetail(ProductType.LAPTOP, 3, PRICE_PROFESSIONAL_LOW_LAPTOP, expectedTotal)
        );
        return new CartTotalResponse(expectedTotal, expectedItems);
    }

    // Invalid request JSON strings for validation tests
    public static String createInvalidRequestWithAllMissingFields() {
        return """
                {
                  "items": [
                    {
                      "quantity": 0
                    }
                  ]
                }
                """;
    }

    public static String createInvalidRequestWithInvalidClientType() {
        return """
                {
                  "client": {
                    "type": "INVALID_TYPE",
                    "clientId": "IND001",
                    "firstName": "John",
                    "lastName": "Doe"
                  },
                  "items": [
                    {
                      "productType": "LAPTOP",
                      "quantity": 1
                    }
                  ]
                }
                """;
    }

    public static String createInvalidRequestWithNullItems() {
        return """
                {
                  "client": {
                    "type": "INDIVIDUAL",
                    "clientId": "IND001",
                    "firstName": "John",
                    "lastName": "Doe"
                  }
                }
                """;
    }

    public static String createInvalidRequestWithEmptyItems() {
        return """
                {
                  "client": {
                    "type": "INDIVIDUAL",
                    "clientId": "IND001",
                    "firstName": "John",
                    "lastName": "Doe"
                  },
                  "items": []
                }
                """;
    }

    public static String createInvalidProfessionalClientRequest() {
        return """
                {
                  "client": {
                    "type": "PROFESSIONAL",
                    "companyName": "",
                    "registrationNumber": "   ",
                    "annualRevenue": -1000
                  },
                  "items": [
                    {
                      "productType": "LAPTOP",
                      "quantity": 1
                    }
                  ]
                }
                """;
    }

    public static String createInvalidIndividualClientRequest() {
        return """
                {
                  "client": {
                    "type": "INDIVIDUAL",
                    "clientId": "",
                    "firstName": "   ",
                    "lastName": ""
                  },
                  "items": [
                    {
                      "productType": "LAPTOP",
                      "quantity": 1
                    }
                  ]
                }
                """;
    }
}
