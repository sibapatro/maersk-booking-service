package com.maersk.booking.service;

import com.maersk.booking.dto.ContainerBookingWithTimestampRequest;
import com.maersk.booking.dto.BookingResponse;
import com.maersk.booking.model.Booking;
import com.maersk.booking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(bookingRepository);
    }

    @Test
    void createBooking_Success() {
        ContainerBookingWithTimestampRequest request = new ContainerBookingWithTimestampRequest();
        request.setContainerSize(20);
        request.setContainerType("DRY");
        request.setOrigin("Southampton");
        request.setDestination("Singapore");
        request.setQuantity(5);
        request.setTimestamp("2020-10-12T13:53:09");

        Booking savedBooking = new Booking("957000001", 20, "DRY", "Southampton", "Singapore", 5, LocalDateTime.now());
        
        when(bookingRepository.save(any(Booking.class))).thenReturn(Mono.just(savedBooking));

        Mono<BookingResponse> result = bookingService.createBooking(request);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getBookingRef().startsWith("957"))
                .verifyComplete();
    }
}