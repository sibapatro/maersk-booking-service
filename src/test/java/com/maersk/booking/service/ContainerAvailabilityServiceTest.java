package com.maersk.booking.service;

import com.maersk.booking.config.ExternalServiceConfig;
import com.maersk.booking.dto.ContainerBookingRequest;
import com.maersk.booking.dto.ExternalAvailabilityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContainerAvailabilityServiceTest {

    @Mock
    private ExternalServiceConfig externalServiceConfig;

    private ContainerAvailabilityService containerAvailabilityService;

    @BeforeEach
    void setUp() {
        containerAvailabilityService = new ContainerAvailabilityService(externalServiceConfig);
    }

    @Test
    void checkAvailability_WithAvailableSpace_ReturnsTrue() {
        // Prepare test data
        ContainerBookingRequest request = new ContainerBookingRequest();
        request.setContainerSize(20);
        request.setContainerType("DRY");
        request.setOrigin("Southampton");
        request.setDestination("Singapore");
        request.setQuantity(5);

        // Mock external service config
        when(externalServiceConfig.getBaseUrl()).thenReturn("https://maersk.com/api");
        when(externalServiceConfig.getCheckAvailableEndpoint()).thenReturn("/bookings/checkAvailable");

        // Create a simulated external response
        ExternalAvailabilityResponse externalResponse = new ExternalAvailabilityResponse();
        externalResponse.setAvailableSpace(10); // More space than requested quantity

        // Create mock WebClient that returns our simulated response
        WebClient webClientMock = WebClient.create();
        webClientMock = WebClient.builder()
            .exchangeFunction(clientRequest ->
                Mono.just(ClientResponse.create(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body("{\"availableSpace\": 10}")
                    .build())
            )
            .build();

        // Inject dependencies
        ReflectionTestUtils.setField(containerAvailabilityService, "externalServiceConfig", externalServiceConfig);
        ReflectionTestUtils.setField(containerAvailabilityService, "webClient", webClientMock);
        
        // Execute and verify the response
        containerAvailabilityService.checkAvailability(request)
            .as(StepVerifier::create)
            .expectNextMatches(response -> response.isAvailable()) // Should be available since 10 > 5
            .verifyComplete();
    }
}