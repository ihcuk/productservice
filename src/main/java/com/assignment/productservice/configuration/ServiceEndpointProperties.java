package com.assignment.productservice.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application.services")
@Data
public class ServiceEndpointProperties {
    private String customerServiceEndpointByName;
}
