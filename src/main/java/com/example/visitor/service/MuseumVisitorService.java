package com.example.visitor.service;


import com.example.visitor.dto.MuseumDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * This Service shows how to use the CircuitBreaker annotation.
 */
@Service
public class MuseumVisitorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumVisitorService.class);

    @Value("${museum.service.url}")
    private String serviceUrl;

    @Value("${alternative.museum.service.url}")
    private String alternativeServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "museumService", fallbackMethod = "getFallbackMuseum")
    public MuseumDTO getMuseum(String address) {
        URI targetUrl= UriComponentsBuilder.fromUriString(serviceUrl)
                .path(address)
                .build()
                .encode().toUri();
        LOGGER.info(targetUrl.toString());
        return this.restTemplate.getForObject(targetUrl, MuseumDTO.class);
    }

    private MuseumDTO getFallbackMuseum(String address, Exception e) {
        URI targetUrl= UriComponentsBuilder.fromUriString(alternativeServiceUrl)
                .path(address)
                .build()
                .encode().toUri();
        return this.restTemplate.getForObject(targetUrl, MuseumDTO.class);
    }

}