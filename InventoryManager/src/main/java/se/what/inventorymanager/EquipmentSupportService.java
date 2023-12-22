package se.what.inventorymanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquipmentSupportService {
    @Autowired
    EquipmentSupportRepo equipmentSupportRepo;

  /*  public EquipmentSupport addEquipmentSupport(int equipment_id, EquipmentStatus status,
                                                int support_record) {
        EquipmentSupport equipmentSupport = new EquipmentSupport(equipment_id, status, support_record);
        return equipmentSupportRepo.save(equipmentSupport);
   */ }


}
