package com.serje3.carsharing.repository;

import com.serje3.carsharing.entity.VehicleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface VehicleRepo extends CrudRepository<VehicleEntity, UUID> {
}
