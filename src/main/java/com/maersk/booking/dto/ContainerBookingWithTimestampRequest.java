package com.maersk.booking.dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.constraints.NotNull;

public class ContainerBookingWithTimestampRequest extends ContainerBookingRequest {
    @NotNull(message = "Timestamp is required")
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime parseTimestamp() {
        try {
            return LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            // Try with Z suffix
            return LocalDateTime.parse(timestamp.replace("Z", ""), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
}