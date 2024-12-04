package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setName("Laptop");
        product.setDescription("A high-end laptop");
        product.setPrice(1200.00);
        product.setQuantity(10);
        productRepository.save(product);
    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(get("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.description").value("A high-end laptop"))
                .andExpect(jsonPath("$.price").value(1200.00))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    public void testCreateProduct() throws Exception {
        String productJson = "{\"name\":\"Laptop\",\"description\":\"A high-end laptop\",\"price\":1200.00,\"quantity\":10}";

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                        .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/" + product.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }
}

