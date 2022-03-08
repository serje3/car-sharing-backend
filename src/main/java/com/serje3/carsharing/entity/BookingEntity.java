package com.serje3.carsharing.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Booking")
public class BookingEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID orderId;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private VehicleEntity vehicle;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    private Integer totalCost;

    public BookingEntity() {
    }

    public void setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
        vehicle.setBookingEntity(this);
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public void removeVehicle(){
        this.vehicle.setBookingEntity(null);
        this.vehicle = null;
    }


    public Date getCreatedAt() {
        return createdAt;
    }



    public Integer getTotalCost() {
//        this.vehicle.getPrice() * this.vehicle.getPriceUnit()
        return totalCost;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }
}
