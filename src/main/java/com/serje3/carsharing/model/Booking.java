package com.serje3.carsharing.model;

import com.serje3.carsharing.entity.BookingEntity;
import com.serje3.carsharing.enums.Currency;

import java.util.Date;
import java.util.UUID;

public class Booking {
    private UUID orderId;
    private UUID carId;
    private Date createdAt;
    private Integer total_cost;
    private Currency currency;

    public static Booking toModel(BookingEntity bookingEntity) {
        Booking booking = new Booking();
        booking.orderId = bookingEntity.getOrderId();
        booking.carId = bookingEntity.getVehicle().getId();
        booking.createdAt = bookingEntity.getCreatedAt();
        booking.total_cost = bookingEntity.getTotalCost();
        booking.currency = bookingEntity.getVehicle().getCurrency();
        return booking;
    }

    public Booking(){
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getCarId() {
        return carId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Integer getTotal_cost() {
        return total_cost;
    }

    public Currency getCurrency() {
        return currency;
    }
}
