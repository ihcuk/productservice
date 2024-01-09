package com.assignment.productservice.service;

import com.assignment.productservice.configuration.ServiceEndpointProperties;
import com.assignment.productservice.dto.APIResponse;
import com.assignment.productservice.dto.ProductRequestDto;
import com.assignment.productservice.entity.Product;
import com.assignment.productservice.repository.ProductRepository;
import com.assignment.productservice.utils.RestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RestUtil restUtil;

    @Mock
    private ServiceEndpointProperties serviceEndpointProperties;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertProductByCustomerName() {
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setCustomerName("John Doe");
        productRequestDto.setProductName("Product1");

        APIResponse apiResponse = new APIResponse();
        apiResponse.setHttpStatus(HttpStatus.OK);
        apiResponse.setResponseBody("123");

        when(serviceEndpointProperties.getCustomerServiceEndpointByName()).thenReturn("http://example.com/customers/");
        when(restUtil.callToRestService(eq(HttpMethod.GET), anyString(), any(), eq(Integer.class)))
                .thenReturn(apiResponse);

        productService.insertProductByCustomerName(productRequestDto);

        verify(productRepository, times(1)).save(any());
    }

    @Test
    void testSearchProductByCustomerName() {
        String customerName = "John Doe";
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        Product product2 = new Product();
        productList.add(product1);
        productList.add(product2);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setHttpStatus(HttpStatus.OK);
        apiResponse.setResponseBody("123");

        when(serviceEndpointProperties.getCustomerServiceEndpointByName()).thenReturn("http://example.com/customers/");
        when(restUtil.callToRestService(eq(HttpMethod.GET), anyString(), any(), eq(Integer.class)))
                .thenReturn(apiResponse);
        when(productRepository.findByUserid(anyInt())).thenReturn(productList);

        List<Product> result = productService.SearchProductByCustomerName(customerName);

        verify(productRepository, times(1)).findByUserid(anyInt());
        assertEquals(productList, result);
    }
}
