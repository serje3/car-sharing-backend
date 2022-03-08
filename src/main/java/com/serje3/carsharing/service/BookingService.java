package com.serje3.carsharing.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.serje3.carsharing.controller.VehicleController;
import com.serje3.carsharing.entity.BookingEntity;
import com.serje3.carsharing.entity.VehicleEntity;
import com.serje3.carsharing.enums.PriceUnit;
import com.serje3.carsharing.exception.BookingNotFoundException;
import com.serje3.carsharing.exception.VehicleNotFoundException;
import com.serje3.carsharing.model.Booking;
import com.serje3.carsharing.model.Vehicle;
import com.serje3.carsharing.repository.BookingRepo;
import com.serje3.carsharing.repository.VehicleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
    private final VehicleRepo vehicleRepo;
    private final BookingRepo bookingRepo;

    Logger logger = LoggerFactory.getLogger(BookingService.class);


    public BookingService(VehicleRepo vehicleRepo, BookingRepo bookingRepo) {
        this.vehicleRepo = vehicleRepo;
        this.bookingRepo = bookingRepo;
    }

    public Booking book(UUID carId) throws VehicleNotFoundException {
        Optional<VehicleEntity> vehicleEntityOptional = vehicleRepo.findById(carId);
        if (vehicleEntityOptional.isEmpty()) {
            throw new VehicleNotFoundException("Автомобиль не существует");
        }
        VehicleEntity vehicleEntity = vehicleEntityOptional.get();
        BookingEntity bookingEntity;
        if (vehicleEntity.getBookingEntity() != null) {
            bookingEntity = bookingRepo.findById(vehicleEntity.getBookingEntity().getOrderId()).get();
            return Booking.toModel(bookingEntity);
        }
        bookingEntity = new BookingEntity();
        bookingEntity.setVehicle(vehicleEntity);
        bookingEntity.setTotalCost(vehicleEntity.getPrice());
        bookingRepo.save(bookingEntity);
        return Booking.toModel(bookingEntity);
    }

    public Booking endBooking(UUID orderId) throws BookingNotFoundException {
        Optional<BookingEntity> bookingEntityOptional = bookingRepo.findById(orderId);
        if (bookingEntityOptional.isEmpty()) {
            logger.warn("АУУУУУ");
            throw new BookingNotFoundException("Заказ уже завершен");
        }
        BookingEntity bookingEntity = bookingEntityOptional.get();
        Booking bookingModel = Booking.toModel(bookingEntity);
        bookingEntity.removeVehicle();
        bookingRepo.delete(bookingEntity);
        return bookingModel;
    }

    public Booking updateBooking(UUID orderId) throws BookingNotFoundException {
        Optional<BookingEntity> bookingEntityOptional = bookingRepo.findById(orderId);
        if (bookingEntityOptional.isEmpty()) {
            throw new BookingNotFoundException("Заказ уже завершен");
        }
        BookingEntity bookingEntity = bookingEntityOptional.get();

        bookingEntity.setTotalCost(this.calculatePriceBooking(bookingEntity));
        return Booking.toModel(bookingRepo.save(bookingEntity));
    }


    private Integer calculatePriceBooking(BookingEntity bookingEntity){
        VehicleEntity vehicleEntity = bookingEntity.getVehicle();

        PriceUnit priceUnit = vehicleEntity.getPriceUnit();
        int priceUnitOrdinal = priceUnit.ordinal();
        long timeBookStart = bookingEntity.getCreatedAt().getTime();
        long timeNow = new Date().getTime();


        int timeUnit;
        if (priceUnit == PriceUnit.DAY) {
            timeUnit = (int) Math.pow(60, 2) * 24;
        } else {
            timeUnit = (int) Math.pow(60, priceUnitOrdinal);
        }

        int differenceTime = (int) (((timeNow - timeBookStart) / 1000.0) / timeUnit);

        return vehicleEntity.getPrice() * (1 + differenceTime);
    }

    public HashMap<String,String> exceptionWrap(String message){
        HashMap<String, String> exceptionJson = new HashMap<>();
        exceptionJson.put("error", message);
        return exceptionJson;
    }
}
