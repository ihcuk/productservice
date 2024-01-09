package com.assignment.productservice.controller;

import com.assignment.productservice.dto.ProductRequestDto;
import com.assignment.productservice.entity.Product;
import com.assignment.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testInsertProductByCustomerName() throws Exception {
        ProductRequestDto productRequestDto = getProductRequestDto();
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productRequestDto)))
                .andExpect(status().isCreated());
    }


    @Test
    public void testSearchProductByCustomerName() throws Exception {
        String customerName = "John Doe";
        List<Product> productList = Arrays.asList(new Product(), new Product());
        when(productService.SearchProductByCustomerName(customerName)).thenReturn(productList);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/products")
                .param("customerName", customerName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(((List<?>) productList).size()));
    }

    public static ProductRequestDto getProductRequestDto() {
        return ProductRequestDto.builder()
                .productName("Hair Oil")
                .customerName("shashank")
                .build();
    }

    // Utility method to convert an object to JSON string
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
