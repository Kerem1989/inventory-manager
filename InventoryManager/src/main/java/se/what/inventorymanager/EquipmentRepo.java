package se.what.inventorymanager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EquipmentRepo extends JpaRepository<Equipment, Integer> {
/*    List<Equipment> findByName(String name);
    List<Equipment> findByType(EquipmentType type);
    List<Equipment> findAll(Equipment equipment);
    int countByType(EquipmentType type);
    boolean existsByName(String name);*/

}
