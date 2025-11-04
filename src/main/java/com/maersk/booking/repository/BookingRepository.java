package com.maersk.booking.repository;

import com.maersk.booking.model.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {
}