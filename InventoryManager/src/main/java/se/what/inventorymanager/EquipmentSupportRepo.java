package se.what.inventorymanager;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentSupportRepo extends JpaRepository<EquipmentSupport, Integer> {
    List<EquipmentSupport> findByEquipmentId(int equipmentId);

    List<EquipmentSupport> findByStatus(boolean status);

    List<EquipmentSupport> findBySupportRecord(int supportRecord);
}
