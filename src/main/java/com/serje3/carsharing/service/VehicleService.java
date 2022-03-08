package com.serje3.carsharing.service;

import com.serje3.carsharing.entity.EquipmentEntity;
import com.serje3.carsharing.entity.VehicleEntity;
import com.serje3.carsharing.enums.Equipment;
import com.serje3.carsharing.exception.VehicleNotFoundException;
import com.serje3.carsharing.model.Marker;
import com.serje3.carsharing.model.Vehicle;
import com.serje3.carsharing.repository.EquipmentRepo;
import com.serje3.carsharing.repository.VehicleRepo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VehicleService {
    private final VehicleRepo vehicleRepo;

    private final EquipmentRepo equipmentRepo;

    public VehicleService(VehicleRepo vehicleRepo, EquipmentRepo equipmentRepo) {
        this.vehicleRepo = vehicleRepo;
        this.equipmentRepo = equipmentRepo;
    }

    public void addVehicle(VehicleEntity vehicleEntity) {
        vehicleRepo.save(vehicleEntity);
    }

    public Vehicle bindEquipment(Equipment equipment, UUID id) throws VehicleNotFoundException {
        Optional<VehicleEntity> vehicle = vehicleRepo.findById(id);
        EquipmentEntity equipmentEntity = equipmentRepo.findByEquipment(equipment);
        if (vehicle.isEmpty()){
            throw new VehicleNotFoundException("Некорректный uuid или автомобиля не существует");
        }
        VehicleEntity vehicleEntity = vehicle.get();
        vehicleEntity.addEquipment(equipmentEntity);
        return Vehicle.toModel(vehicleRepo.save(vehicleEntity));
    }

    public List<Marker> getVehiclePositions(){
        List<Marker> markers = new ArrayList<>();
        vehicleRepo.findAll().forEach(v -> markers.add(Marker.toModel(v)));
        return markers;
    }

    public Vehicle getVehicle(UUID id) throws VehicleNotFoundException {
        Optional<VehicleEntity> vehicle = vehicleRepo.findById(id);
        if (vehicle.isEmpty()) {
            throw new VehicleNotFoundException("Автомобиля с таким UUID не существует");
        }
        return Vehicle.toModel(vehicle.get());
    }
}
