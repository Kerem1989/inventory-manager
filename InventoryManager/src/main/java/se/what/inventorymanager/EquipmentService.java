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
            EquipmentState state, EquipmentType type, User user) {
        Equipment equipment = new Equipment(name, purchaseDate, purchasePrice, state, type, user);
        return equipmentRepo.save(equipment);
    }

    public Equipment getEquipmentById(Integer id) {
        return equipmentRepo.findById(id).orElse(null);
    }


    //caused crash on run and had no usages atm, commented it out :) / robert
//    public List<Equipment> getEquipmentByType(EquipmentType type) {
//        return equipmentRepo.findByType(type);
//    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepo.findAll();
    }

    public Equipment updateEquipment(Integer id, String name, Date purchaseDate,
                                     double purchasePrice, EquipmentState state,
                                     EquipmentType type, User user) {
        Equipment equipment = equipmentRepo.findById(id).orElse(null);
        if (equipment != null) {
            equipment.setName(name);
            equipment.setPurchaseDate(purchaseDate);
            equipment.setPurchasePrice(purchasePrice);
            equipment.setState(state);
            equipment.setType(type);
            equipment.setUser(user);
            equipmentRepo.save(equipment);
        }
        return equipment;
    }

    public void deleteEquipment(Integer id) {
        equipmentRepo.deleteById(id);
    }

}
