package se.what.inventorymanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepo equipmentRepo;

    public Equipment addEquipment(String name, Date purchaseDate, double purchasePrice,
            EquipmentState state, EquipmentType type) {
        Equipment equipment = new Equipment(name, purchaseDate, purchasePrice, state, type);
        return equipmentRepo.save(equipment);
    }

    public Equipment getEquipmentById(Integer id) {
        return equipmentRepo.findById(id).orElse(null);
    }

    public List<Equipment> getEquipmentByType(EquipmentType type) {
        return equipmentRepo.findByType(type);
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepo.findAll();
    }

    public Equipment updateEquipment(Integer id, String name, Date purchaseDate,
                                     double purchasePrice, EquipmentState state,
                                     EquipmentType type) {
        Equipment equipment = equipmentRepo.findById(id).orElse(null);
        if (equipment != null) {
            equipment.setName(name);
            equipment.setPurchaseDate(purchaseDate);
            equipment.setPurchasePrice(purchasePrice);
            equipment.setState(state);
            equipment.setType(type);
            equipmentRepo.save(equipment);
        }
        return equipment;
    }

    public void deleteEquipment(Integer id) {
        equipmentRepo.deleteById(id);
    }

}
