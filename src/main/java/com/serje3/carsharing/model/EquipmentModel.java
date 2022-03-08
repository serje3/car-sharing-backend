package com.serje3.carsharing.model;

import com.serje3.carsharing.entity.EquipmentEntity;
import com.serje3.carsharing.enums.Equipment;

public class EquipmentModel {
    private Equipment equipment;

    public static EquipmentModel toModel(EquipmentEntity equipmentEntity){
        return new EquipmentModel(equipmentEntity.getEquipment());
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public EquipmentModel(Equipment equipment) {
        this.equipment = equipment;
    }

    public EquipmentModel() {
    }

    public Equipment getEquipment() {
        return equipment;
    }
}
