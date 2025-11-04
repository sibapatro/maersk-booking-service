package com.maersk.booking.service;

import com.maersk.booking.config.ExternalServiceConfig;
import com.maersk.booking.dto.ContainerBookingRequest;
import com.maersk.booking.dto.ExternalAvailabilityResponse;
import com.maersk.booking.dto.AvailabilityResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class ContainerAvailabilityService {

    private final ExternalServiceConfig externalServiceConfig;
    private final WebClient webClient;

    public ContainerAvailabilityService(ExternalServiceConfig externalServiceConfig) {
        this.externalServiceConfig = externalServiceConfig;
        this.webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
    }

    public Mono<AvailabilityResponse> checkAvailability(ContainerBookingRequest request) {
        return webClient.post()
                .uri(externalServiceConfig.getBaseUrl() + externalServiceConfig.getCheckAvailableEndpoint())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ExternalAvailabilityResponse.class)
                .map(response -> new AvailabilityResponse(response.getAvailableSpace() > 0))
                .retryWhen(Retry.backoff(
                        externalServiceConfig.getMaxRetries(),
                        Duration.ofMillis(externalServiceConfig.getBackoffPeriod())
                ).maxBackoff(Duration.ofSeconds(10)))
                .onErrorReturn(new AvailabilityResponse(false));
    }
}