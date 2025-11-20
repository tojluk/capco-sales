package com.capco.sales.service;

import com.capco.sales.model.Product;
import com.capco.sales.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product("Test Product", "Test Description", 99.99);
        testProduct.setId(1L);
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        // Given
        List<Product> products = Arrays.asList(testProduct, new Product("Product 2", "Description 2", 49.99));
        when(productRepository.findAll()).thenReturn(products);

        // When
        List<Product> result = productService.getAllProducts();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(products);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // When
        Optional<Product> result = productService.getProductById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_WhenProductDoesNotExist_ShouldReturnEmpty() {
        // Given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Optional<Product> result = productService.getProductById(99L);

        // Then
        assertThat(result).isEmpty();
        verify(productRepository, times(1)).findById(99L);
    }

    @Test
    void createProduct_ShouldSaveAndReturnProduct() {
        // Given
        Product newProduct = new Product("New Product", "New Description", 29.99);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // When
        Product result = productService.createProduct(newProduct);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(productRepository, times(1)).save(newProduct);
    }

    @Test
    void updateProduct_WhenProductExists_ShouldUpdateAndReturnProduct() {
        // Given
        Product updatedDetails = new Product("Updated Product", "Updated Description", 79.99);
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // When
        Optional<Product> result = productService.updateProduct(1L, updatedDetails);

        // Then
        assertThat(result).isPresent();
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void updateProduct_WhenProductDoesNotExist_ShouldReturnEmpty() {
        // Given
        Product updatedDetails = new Product("Updated Product", "Updated Description", 79.99);
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Optional<Product> result = productService.updateProduct(99L, updatedDetails);

        // Then
        assertThat(result).isEmpty();
        verify(productRepository, times(1)).findById(99L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void deleteProduct_WhenProductExists_ShouldReturnTrue() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        doNothing().when(productRepository).delete(any(Product.class));

        // When
        boolean result = productService.deleteProduct(1L);

        // Then
        assertThat(result).isTrue();
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(testProduct);
    }

    @Test
    void deleteProduct_WhenProductDoesNotExist_ShouldReturnFalse() {
        // Given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        boolean result = productService.deleteProduct(99L);

        // Then
        assertThat(result).isFalse();
        verify(productRepository, times(1)).findById(99L);
        verify(productRepository, never()).delete(any(Product.class));
    }

    @Test
    void searchProducts_ShouldReturnMatchingProducts() {
        // Given
        List<Product> products = Arrays.asList(testProduct);
        when(productRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(products);

        // When
        List<Product> result = productService.searchProducts("Test");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findByNameContainingIgnoreCase("Test");
    }
}
