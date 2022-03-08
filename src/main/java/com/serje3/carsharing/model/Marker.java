package com.serje3.carsharing.model;

import com.serje3.carsharing.entity.VehicleEntity;

import java.util.UUID;

public class Marker {
    private final UUID id;
    private final Double latitude;
    private final Double longitude;

    public Marker(UUID id, Double latitude, Double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Marker toModel(VehicleEntity entity){
        return new Marker(
                entity.getId(),
                entity.getLatitude(),
                entity.getLongitude()
        );
    }

    public UUID getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
