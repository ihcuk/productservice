package com.assignment.productservice.service;

import com.assignment.productservice.configuration.ServiceEndpointProperties;
import com.assignment.productservice.dto.APIResponse;
import com.assignment.productservice.dto.ProductRequestDto;
import com.assignment.productservice.entity.Product;
import com.assignment.productservice.repository.ProductRepository;
import com.assignment.productservice.utils.RestUtil;
import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestUtil restUtil;

    @Autowired
    private ServiceEndpointProperties serviceEndpointProperties;

    public ProductService(ProductRepository productRepository, RestUtil restUtil, ServiceEndpointProperties serviceEndpointProperties) {
        this.productRepository = productRepository;
        this.restUtil = restUtil;
        this.serviceEndpointProperties = serviceEndpointProperties;
    }

    public void insertProductByCustomerName(ProductRequestDto productRequestDto) {

        APIResponse customerServiceResponse = fetchCustomerIdByCustomerName(productRequestDto.getCustomerName());
        if(customerServiceResponse.getHttpStatus().is2xxSuccessful()) {
            productRepository.save(Product.builder()
                    .userid(Integer.parseInt(customerServiceResponse.getResponseBody()))
                    .productname(productRequestDto.getProductName())
                    .build());
        }
    }

    private APIResponse fetchCustomerIdByCustomerName(String customerName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(httpHeaders);
        String customerIdByNameUrl = serviceEndpointProperties.getCustomerServiceEndpointByName()
                + customerName;
        return restUtil.callToRestService(HttpMethod.GET,
                customerIdByNameUrl, requestEntity, Integer.class);
    }

    public List<Product> SearchProductByCustomerName(String customerName) {
        List<Product> productList = new ArrayList<>();
        APIResponse customerServiceResponse = fetchCustomerIdByCustomerName(customerName);
        if(customerServiceResponse.getHttpStatus().is2xxSuccessful()) {
            productList = productRepository.findByUserid(Integer.parseInt(customerServiceResponse.getResponseBody()));
        }
        return productList;
    }
}
