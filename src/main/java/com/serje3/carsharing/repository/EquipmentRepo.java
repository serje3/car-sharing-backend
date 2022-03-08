package com.serje3.carsharing.repository;

import com.serje3.carsharing.entity.EquipmentEntity;
import com.serje3.carsharing.enums.Equipment;
import org.springframework.data.repository.CrudRepository;

public interface EquipmentRepo extends CrudRepository<EquipmentEntity, Long> {
    EquipmentEntity findByEquipment(Equipment equipment);
}
