package se.what.inventorymanager;

import Utils.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static Utils.InputOutput.input;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepo equipmentRepo;


    public void addNewEquipment(EquipmentRepo equipmentRepo, UserRepo userRepo) {
        Equipment equipment = new Equipment();
        String inputName = InputOutput.getUserData("Please enter name of the equipment: ");
        equipment.setName(inputName);

        Date purchaseDate = InputOutput.asDate(LocalDate.now());
        equipment.setPurchaseDate(purchaseDate);

        System.out.println("Please enter price of the equipment: ");
        double inputpurchasePrice = InputOutput.getValidDoubleInput(input, 1);
        equipment.setPurchasePrice(inputpurchasePrice);

        EquipmentState state = EquipmentState.AVAILABLE;
        equipment.setState(state);

        String inputType = InputOutput.getUserData("Please enter equipment type (LAPTOP, PHONE, MONITOR, PROJECTOR, OFFICE_CHAIR):");
        EquipmentType type = EquipmentType.fromString(inputType); // toUpperCase() ?
        equipment.setType(type);

        System.out.println("Please enter the ID of the user purchasing the equipment: ");
        int userId = InputOutput.getValidIntegerInput(input, 1, 16);
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            equipment.setUser(user);
        } else {
            System.out.println("No user found with the given ID.");
            return;
        }

        equipmentRepo.save(equipment);
        System.out.println(equipment + " added");
    }

    public void displayEquipment(EquipmentRepo equipmentRepo) {
        System.out.println(equipmentRepo.findAll());
    }

    public void updateEquipment() {}

    public void deleteEquipment(Integer id) {
        equipmentRepo.deleteById(id);
    }

}
