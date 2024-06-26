package com.hli.pact.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ContractConsumerConfig {

    @Bean
    public RestTemplate restTemplate(@Value("${producer.service.url}") String producerServiceURL) {
        return new RestTemplateBuilder().rootUri(producerServiceURL).build();
    }
}
