package com.assignment.productservice.utils;

import com.assignment.productservice.constants.ProductServiceConstants;
import com.assignment.productservice.dto.APIResponse;
import com.assignment.productservice.exceptions.WrongServiceUrlEndpointException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class RestUtil {
    @Autowired
    private RestTemplate restTemplate;

    public RestUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public APIResponse callToRestService(HttpMethod httpMethod, String url, HttpEntity<?> httpEntity, Class<?> responseObject) {
        APIResponse apiResponse = null;
        boolean canRetry = true;
        boolean isTokenRefreshPerformed = false;
        while (canRetry) {
            try {
                if (ProductServiceConstants.urlWhiteList.contains(url)) {
                    throw new WrongServiceUrlEndpointException("Wrong Input");
                }
                ResponseEntity<?> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, responseObject);
                apiResponse = new APIResponse((HttpStatus) responseEntity.getStatusCode(), new ObjectMapper().writeValueAsString(responseEntity.getBody()));

                canRetry = false;

            } catch (HttpClientErrorException httpEx) {
                log.warn("HttpClientError from invoked api due to:"+httpEx.getMessage()+ " http status:"+httpEx.getStatusCode()+ " token refresh flow:"+isTokenRefreshPerformed);
            } catch (JsonProcessingException ignored) {
                log.warn("Not Able to procees durther due to response processing failure");
            }
        }
        return apiResponse;
    }
}
