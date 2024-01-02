package se.what.inventorymanager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepo extends JpaRepository<Equipment, Integer> {
    Optional<Equipment> findById(int id);
//    List<Equipment> findByName(String name);
//    List<Equipment> findByType(EquipmentType type);
//    List<Equipment> findAll(Equipment equipment)
    List<Equipment> findByState (EquipmentState state);
    boolean existsEquipmentByUser (Optional<User> user);


}




