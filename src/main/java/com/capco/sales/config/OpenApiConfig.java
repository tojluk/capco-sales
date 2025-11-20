package com.capco.sales.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration for API documentation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI capcoSalesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Capco Sales API")
                        .description("Shopping cart calculation API for online product sales")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Capco Sales Team")
                                .email("sales@capco.com")));
    }
}
