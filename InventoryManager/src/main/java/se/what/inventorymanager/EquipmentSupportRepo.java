package se.what.inventorymanager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipmentSupportRepo extends JpaRepository<EquipmentSupport, Integer> {
    List<EquipmentSupport> findByEquipmentId(int equipmentId);

    List<EquipmentSupport> findByStatus(boolean status);

    List<EquipmentSupport> findBySupportRecord(int supportRecord);
}
 