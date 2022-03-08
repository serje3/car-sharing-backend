package com.serje3.carsharing.controller;

import com.serje3.carsharing.entity.VehicleEntity;
import com.serje3.carsharing.model.EquipmentModel;
import com.serje3.carsharing.service.BookingService;
import com.serje3.carsharing.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    Logger logger = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleService vehicleService;
    private final BookingService bookingService;

    public VehicleController(VehicleService vehicleService, BookingService bookingService) {
        this.vehicleService = vehicleService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<?> getVehiclePositions(){
        return ResponseEntity.ok(vehicleService.getVehiclePositions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicle(@PathVariable UUID id){
        try{
            return ResponseEntity.ok(vehicleService.getVehicle(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(bookingService.exceptionWrap(e.getMessage()));
        }
    }

    @PostMapping("/{carId}/booking")
    public ResponseEntity<?> bookCar(@PathVariable UUID carId){
        try{
            return ResponseEntity.ok(bookingService.book(carId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(bookingService.exceptionWrap(e.getMessage()));
        }
    }

    @DeleteMapping("/{orderId}/booking")
    public ResponseEntity<?> endBooking(@PathVariable UUID orderId){
        try{
            return ResponseEntity.ok(bookingService.endBooking(orderId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(bookingService.exceptionWrap(e.getMessage()));
        }
    }

    @PutMapping("/{orderId}/booking")
    public ResponseEntity<?> updateBooking(@PathVariable UUID orderId){
        try{
            return ResponseEntity.ok(bookingService.updateBooking(orderId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(bookingService.exceptionWrap(e.getMessage()));
        }
    }

    @PostMapping("/add/")
    public ResponseEntity<?> addVehicle(@RequestBody VehicleEntity vehicleEntity){
        try{
            vehicleService.addVehicle(vehicleEntity);
            return ResponseEntity.ok(vehicleEntity);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(bookingService.exceptionWrap(e.getMessage()));
        }
    }

    @PostMapping("/equipment/{vehicleId}")
    public ResponseEntity<?> bindEquipment(@RequestBody EquipmentModel equipment,
                                           @PathVariable UUID vehicleId){
        try{
            logger.warn(equipment.toString());
            return ResponseEntity.ok(vehicleService.bindEquipment(equipment.getEquipment(), vehicleId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(bookingService.exceptionWrap(e.getMessage()));
        }
    }
}
