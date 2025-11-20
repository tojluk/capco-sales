package com.capco.sales.controller;

import com.capco.sales.model.Product;
import com.capco.sales.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        // Given
        Product product = new Product("Laptop", "High-performance laptop", 1299.99);

        // When & Then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Laptop")))
                .andExpect(jsonPath("$.description", is("High-performance laptop")))
                .andExpect(jsonPath("$.price", is(1299.99)))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() throws Exception {
        // Given
        productRepository.save(new Product("Product 1", "Description 1", 10.00));
        productRepository.save(new Product("Product 2", "Description 2", 20.00));

        // When & Then
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Product 1")))
                .andExpect(jsonPath("$[1].name", is("Product 2")));
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() throws Exception {
        // Given
        Product savedProduct = productRepository.save(new Product("Test Product", "Test Description", 99.99));

        // When & Then
        mockMvc.perform(get("/api/products/" + savedProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedProduct.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Test Product")))
                .andExpect(jsonPath("$.description", is("Test Description")))
                .andExpect(jsonPath("$.price", is(99.99)));
    }

    @Test
    void getProductById_WhenProductDoesNotExist_ShouldReturnNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProduct_WhenProductExists_ShouldReturnUpdatedProduct() throws Exception {
        // Given
        Product savedProduct = productRepository.save(new Product("Old Name", "Old Description", 50.00));
        Product updatedProduct = new Product("New Name", "New Description", 60.00);

        // When & Then
        mockMvc.perform(put("/api/products/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New Name")))
                .andExpect(jsonPath("$.description", is("New Description")))
                .andExpect(jsonPath("$.price", is(60.00)));
    }

    @Test
    void updateProduct_WhenProductDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Given
        Product updatedProduct = new Product("New Name", "New Description", 60.00);

        // When & Then
        mockMvc.perform(put("/api/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_WhenProductExists_ShouldReturnNoContent() throws Exception {
        // Given
        Product savedProduct = productRepository.save(new Product("To Delete", "Will be deleted", 30.00));

        // When & Then
        mockMvc.perform(delete("/api/products/" + savedProduct.getId()))
                .andExpect(status().isNoContent());

        // Verify deletion
        mockMvc.perform(get("/api/products/" + savedProduct.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_WhenProductDoesNotExist_ShouldReturnNotFound() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void searchProducts_ShouldReturnMatchingProducts() throws Exception {
        // Given
        productRepository.save(new Product("Laptop Computer", "Gaming laptop", 1500.00));
        productRepository.save(new Product("Desktop Computer", "Office desktop", 800.00));
        productRepository.save(new Product("Mouse", "Wireless mouse", 25.00));

        // When & Then
        mockMvc.perform(get("/api/products/search")
                        .param("name", "computer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Laptop Computer")))
                .andExpect(jsonPath("$[1].name", is("Desktop Computer")));
    }

    @Test
    void searchProducts_WhenNoMatch_ShouldReturnEmptyList() throws Exception {
        // Given
        productRepository.save(new Product("Laptop", "Gaming laptop", 1500.00));

        // When & Then
        mockMvc.perform(get("/api/products/search")
                        .param("name", "nonexistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
