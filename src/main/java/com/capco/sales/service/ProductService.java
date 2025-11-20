package com.capco.sales.service;

import com.capco.sales.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    public List<Product> getAllProducts() {
        // Return a simple list for demonstration
        List<Product> products = new ArrayList<>();
        products.add(new Product("1"));
        products.add(new Product("2"));
        return products;
    }
}
