// src/main/java/com/maersk/booking/dto/ContainerBookingRequest.java
package com.maersk.booking.dto;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ContainerBookingRequest {
    @NotNull(message = "Container size is required")
    @Min(value = 20, message = "Container size must be 20 or 40")
    @Max(value = 40, message = "Container size must be 20 or 40")
    private Integer containerSize;

    @NotNull(message = "Container type is required")
    @Pattern(regexp = "DRY|REEFER", message = "Container type must be DRY or REEFER")
    private String containerType;

    @NotBlank(message = "Origin is required")
    @Size(min = 5, max = 20, message = "Origin must be between 5 and 20 characters")
    private String origin;

    @NotBlank(message = "Destination is required")
    @Size(min = 5, max = 20, message = "Destination must be between 5 and 20 characters")
    private String destination;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be between 1 and 100")
    @Max(value = 100, message = "Quantity must be between 1 and 100")
    private Integer quantity;

    public Integer getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(Integer containerSize) {
        this.containerSize = containerSize;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}