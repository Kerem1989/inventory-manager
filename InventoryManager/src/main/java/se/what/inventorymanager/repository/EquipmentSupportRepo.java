package se.what.inventorymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.what.inventorymanager.EquipmentStatus;
import se.what.inventorymanager.EquipmentSupport;

import java.util.List;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


@Repository
public interface EquipmentSupportRepo extends JpaRepository<EquipmentSupport, Integer> {
    List<EquipmentSupport> findByEquipmentId(int equipmentId);
    List<EquipmentSupport> findByStatus(EquipmentStatus status);
    List<EquipmentSupport> findBySupportRecord(int supportRecord);
}
 