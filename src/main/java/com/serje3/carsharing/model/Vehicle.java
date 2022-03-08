package com.serje3.carsharing.model;

import com.serje3.carsharing.entity.EquipmentEntity;
import com.serje3.carsharing.entity.VehicleEntity;
import com.serje3.carsharing.enums.Currency;
import com.serje3.carsharing.enums.Equipment;
import com.serje3.carsharing.enums.PriceUnit;
import java.net.URL;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Vehicle {
    private final UUID id;
    private final String name;
    private final URL image;
    private final Integer fuel;
    private final Integer price;
    private final Currency currency;
    private final PriceUnit priceUnit;
    private final Set<Equipment> equipments;

    public Vehicle(UUID id, String name, URL image, Integer fuel, Integer price, Currency currency, PriceUnit priceUnit, Set<EquipmentEntity> equipments) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.fuel = fuel;
        this.price = price;
        this.currency = currency;
        this.priceUnit = priceUnit;
        this.equipments = equipments.stream().map(e -> EquipmentModel.toModel(e).getEquipment()).collect(Collectors.toSet());
    }

    public static Vehicle toModel(VehicleEntity entity){
        return new Vehicle(
                entity.getId(),
                entity.getName(),
                entity.getImage(),
                entity.getFuel(),
                entity.getPrice(),
                entity.getCurrency(),
                entity.getPriceUnit(),
                entity.getEquipments()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public URL getImage() {
        return image;
    }

    public Integer getFuel() {
        return fuel;
    }

    public Integer getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public PriceUnit getPriceUnit() {
        return priceUnit;
    }

    public Set<Equipment> getEquipments() {
        return equipments;
    }
}
