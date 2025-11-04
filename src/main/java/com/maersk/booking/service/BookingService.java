package com.maersk.booking.service;

import com.maersk.booking.dto.ContainerBookingWithTimestampRequest;
import com.maersk.booking.dto.BookingResponse;
import com.maersk.booking.model.Booking;
import com.maersk.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BookingService {
    
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Mono<BookingResponse> createBooking(ContainerBookingWithTimestampRequest request) {
        String bookingRef = generateBookingReference();
        
        Booking booking = new Booking(
            bookingRef,
            request.getContainerSize(),
            request.getContainerType(),
            request.getOrigin(),
            request.getDestination(),
            request.getQuantity(),
            request.parseTimestamp()
        );

        return bookingRepository.save(booking)
                .map(savedBooking -> new BookingResponse(savedBooking.getBookingRef()))
                .onErrorMap(ex -> new RuntimeException("Error saving booking", ex));
    }

    private String generateBookingReference() {
        long timestamp = System.currentTimeMillis();
        String suffix = String.format("%06d", (timestamp % 1000000));
        return "957" + suffix.substring(suffix.length() - 6);
    }
}