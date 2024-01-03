package se.what.inventorymanager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnassignedEquipmentRepo extends JpaRepository <UnassignedEquipment, Integer> {
}
