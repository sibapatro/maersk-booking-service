package com.maersk.booking.dto;

public class BookingResponse {
    private String bookingRef;

    public BookingResponse() {
    }

    public BookingResponse(String bookingRef) {
        this.bookingRef = bookingRef;
    }

    public String getBookingRef() {
        return bookingRef;
    }

    public void setBookingRef(String bookingRef) {
        this.bookingRef = bookingRef;
    }
}