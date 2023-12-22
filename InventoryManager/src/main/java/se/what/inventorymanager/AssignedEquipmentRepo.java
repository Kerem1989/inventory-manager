package se.what.inventorymanager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedEquipmentRepo extends JpaRepository<AssignedEquipment, Integer> {
}
