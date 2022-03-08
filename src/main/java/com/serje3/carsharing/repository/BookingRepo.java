package com.serje3.carsharing.repository;

import com.serje3.carsharing.entity.BookingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BookingRepo extends CrudRepository<BookingEntity, UUID> {
}
