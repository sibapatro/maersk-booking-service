// src/main/java/com/maersk/booking/config/ExternalServiceConfig.java
package com.maersk.booking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "external.maersk.api")
public class ExternalServiceConfig {
    private String baseUrl = "https://maersk.com/api";
    private String checkAvailableEndpoint = "/bookings/checkAvailable";
    private int timeout = 5000; // milliseconds
    private int maxRetries = 3;
    private long backoffPeriod = 1000; // milliseconds

    // Getters and setters
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCheckAvailableEndpoint() {
        return checkAvailableEndpoint;
    }

    public void setCheckAvailableEndpoint(String checkAvailableEndpoint) {
        this.checkAvailableEndpoint = checkAvailableEndpoint;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public long getBackoffPeriod() {
        return backoffPeriod;
    }

    public void setBackoffPeriod(long backoffPeriod) {
        this.backoffPeriod = backoffPeriod;
    }
}