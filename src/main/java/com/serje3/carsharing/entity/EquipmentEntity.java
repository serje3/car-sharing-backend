package com.serje3.carsharing.entity;


import com.serje3.carsharing.enums.Equipment;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipment")
public class EquipmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private Equipment equipment;

    @ManyToMany(mappedBy = "equipments")
    private Set<VehicleEntity> vehicles =new HashSet<>();

    public EquipmentEntity() {

    }

    public Set<VehicleEntity> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<VehicleEntity> vehicles) {
        this.vehicles = vehicles;
    }

    public Equipment getEquipment() {
        return equipment;
    }
}
