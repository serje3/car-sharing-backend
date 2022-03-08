package com.serje3.carsharing.entity;

import com.serje3.carsharing.enums.Currency;
import com.serje3.carsharing.enums.PriceUnit;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.net.URL;
import java.util.*;

@Entity
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinTable(name = "vehicle_booking_entity",
            joinColumns = @JoinColumn(name = "vehicle_entity_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_entity_order_id"))
    private BookingEntity booking;

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

    public Set<EquipmentEntity> getEquipments() {
        return equipments;
    }

    private String name;
    private Double latitude;
    private Double longitude;
    private URL image;
    private Integer fuel;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private PriceUnit priceUnit;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "vehicle_equipment",
            joinColumns = @JoinColumn(name = "vehicle_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private Set<EquipmentEntity> equipments = new HashSet<>();

    public BookingEntity getBookingEntity() {
        return booking;
    }

    public void setBookingEntity(BookingEntity bookingEntity) {
        this.booking = bookingEntity;
    }


    public VehicleEntity() {
    }

    public void addEquipment(EquipmentEntity equipment) {
        this.equipments.add(equipment);
        equipment.getVehicles().add(this);
    }

    public void removeEquipment(EquipmentEntity equipment) {
        this.equipments.remove(equipment);
        equipment.getVehicles().remove(this);
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
