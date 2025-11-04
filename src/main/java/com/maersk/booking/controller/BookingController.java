// src/main/java/com/maersk/booking/controller/BookingController.java
package com.maersk.booking.controller;

import com.maersk.booking.dto.*;
import com.maersk.booking.service.BookingService;
import com.maersk.booking.service.ContainerAvailabilityService;
import javax.validation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final ContainerAvailabilityService containerAvailabilityService;
    private final BookingService bookingService;

    public BookingController(ContainerAvailabilityService containerAvailabilityService, BookingService bookingService) {
        this.containerAvailabilityService = containerAvailabilityService;
        this.bookingService = bookingService;
    }

    @PostMapping("/check")
    public Mono<ResponseEntity<AvailabilityResponse>> checkAvailability(
            @Valid @RequestBody ContainerBookingRequest request) {
        
        return containerAvailabilityService.checkAvailability(request)
                .map(response -> ResponseEntity.ok(response))
                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new AvailabilityResponse(false)));
    }

    @PostMapping
    public Mono<ResponseEntity<BookingResponse>> createBooking(
            @Valid @RequestBody ContainerBookingWithTimestampRequest request) {
        
        return bookingService.createBooking(request)
                .map(response -> ResponseEntity.ok(response))
                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new BookingResponse()));
    }
}