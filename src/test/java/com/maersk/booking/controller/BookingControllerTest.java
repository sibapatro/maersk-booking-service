package com.maersk.booking.controller;

import com.maersk.booking.dto.*;
import com.maersk.booking.service.BookingService;
import com.maersk.booking.service.ContainerAvailabilityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ContainerAvailabilityService containerAvailabilityService;

    @MockBean
    private BookingService bookingService;

    @Test
    void checkAvailability_WithValidRequest_ReturnsAvailability() {
        ContainerBookingRequest request = new ContainerBookingRequest();
        request.setContainerSize(20);
        request.setContainerType("DRY");
        request.setOrigin("Southampton");
        request.setDestination("Singapore");
        request.setQuantity(5);

        AvailabilityResponse response = new AvailabilityResponse(true);

        Mockito.when(containerAvailabilityService.checkAvailability(Mockito.any(ContainerBookingRequest.class)))
                .thenReturn(Mono.just(response));

        webClient.post()
                .uri("/api/bookings/check")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AvailabilityResponse.class)
                .value(avail -> org.junit.jupiter.api.Assertions.assertTrue(avail.isAvailable()));
    }

    @Test
    void createBooking_WithValidRequest_ReturnsBookingRef() {
        ContainerBookingWithTimestampRequest request = new ContainerBookingWithTimestampRequest();
        request.setContainerSize(20);
        request.setContainerType("DRY");
        request.setOrigin("Southampton");
        request.setDestination("Singapore");
        request.setQuantity(5);
        request.setTimestamp("2020-10-12T13:53:09Z");

        BookingResponse response = new BookingResponse("957000001");

        Mockito.when(bookingService.createBooking(Mockito.any(ContainerBookingWithTimestampRequest.class)))
                .thenReturn(Mono.just(response));

        webClient.post()
                .uri("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookingResponse.class)
                .value(booking -> org.junit.jupiter.api.Assertions.assertEquals("957000001", booking.getBookingRef()));
    }
}