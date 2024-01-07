package se.what.inventorymanager.service;

import Utils.InputOutput;
import Utils.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.what.inventorymanager.domain.Equipment;
import se.what.inventorymanager.domain.User;
import se.what.inventorymanager.enums.EquipmentState;
import se.what.inventorymanager.enums.EquipmentType;
import se.what.inventorymanager.repository.EquipmentRepo;
import se.what.inventorymanager.repository.EquipmentSupportRepo;
import se.what.inventorymanager.repository.UnassignedEquipmentRepo;
import se.what.inventorymanager.repository.UserRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static Utils.InputOutput.getValidIntegerInput;
import static Utils.InputOutput.input;

@Service
public class EquipmentService {

    @Autowired
    EquipmentRepo equipmentRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UnassignedEquipmentRepo unassignedEquipmentRepo;


    public static void addNewEquipment(EquipmentRepo equipmentRepo, UserRepo userRepo) {
        Equipment equipment = new Equipment();
        String inputName = InputOutput.getUserDataString("Please enter name of the equipment: ");
        equipment.setName(inputName);

        Date purchaseDate = InputOutput.asDate(LocalDate.now());
        equipment.setPurchaseDate(purchaseDate);

        System.out.print("Please enter price of the equipment: ");
        int inputpurchasePrice = UserInput.readInt();
        equipment.setPurchasePrice(inputpurchasePrice);

        EquipmentState state = EquipmentState.unassigned;
        equipment.setState(state);

        String inputType = InputOutput.getUserDataString("Please enter equipment type (laptop, phone, screen):");
        EquipmentType type = EquipmentType.fromString(inputType);
        equipment.setType(type);

        System.out.print("Please enter the ID of the user purchasing the equipment: ");
        int userId = UserInput.readInt();
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

    public static boolean displayEquipment(EquipmentRepo equipmentRepo) {
        System.out.println(equipmentRepo.findAll());
        return false;
    }

    public static void editEquipment (EquipmentRepo equipmentRepo) {
        boolean runEditMenu = true;
        while (runEditMenu) {
            System.out.println("Menu for editing equipment, please select an equipment id to begin editing:");
            displayEssentialOfEquipment(equipmentRepo);
            System.out.println("Please enter the id of the equipment to begin editing: ");
            int selectEquipmentById = input.nextInt();
            input.nextLine();
            Optional<Equipment> equipmentOptional = equipmentRepo.findById(selectEquipmentById);
            if (equipmentOptional.isPresent()) {
                boolean isEditingCurrentEquipment = true;
                while (isEditingCurrentEquipment) {
                    Equipment equipment = equipmentOptional.get();
                    editEquipmentMenu();
                    int selectMenuOption = getValidIntegerInput(input, 1, 6);
                    switch (selectMenuOption) {
                        case 1 -> {
                            System.out.println("Enter the new name: ");
                            String editName = input.nextLine();
                            equipment.setName(editName);
                            equipmentRepo.save(equipment);
                        }
                        case 2 -> {
                            System.out.println("Enter the new purchase date (format: yyyy-MM-dd): ");
                            String dateString = input.nextLine();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date editDate = sdf.parse(dateString);
                                equipment.setPurchaseDate(editDate);
                                equipmentRepo.save(equipment);
                            } catch (ParseException e) {
                                System.out.println("Invalid date format.");
                            }
                        }
                        case 3 -> {
                            System.out.println("Enter the new price: ");
                            int editPrice = input.nextInt();
                            input.nextLine();
                            equipment.setPurchasePrice(editPrice);
                            equipmentRepo.save(equipment);
                        }
                        case 4 -> {
                            System.out.println("Enter the new equipment state: ");
                            String editState = input.nextLine();
                            equipment.setState(EquipmentState.valueOf(editState));
                            equipmentRepo.save(equipment);
                        }
                        case 5 -> {
                            System.out.println("Enter the new equipment type: ");
                            String editType = input.nextLine();
                            equipment.setType(EquipmentType.valueOf(editType));
                            equipmentRepo.save(equipment);
                        }
                        case 6 -> {
                            System.out.println("Are you sure you want to delete " + equipment.getName() + "? (yes/no)");
                            String confirmation = input.nextLine().trim();
                            if ("yes".equalsIgnoreCase(confirmation)) {
                                deleteEquipment(equipmentRepo, selectEquipmentById);
                                System.out.println("Equipment deleted successfully.");
                                isEditingCurrentEquipment = false;
                            } else {
                                System.out.println("Equipment deletion cancelled.");
                            }
                        }
                        case 7 -> isEditingCurrentEquipment = false;}}
            } else {
                System.out.println("No equipment found with the given ID.");
            }

            System.out.println("Do you want to continue editing equipment? (yes/no)");
            String userDecision = String.valueOf(UserInput.readYesNo());
            if (!userDecision.equals("yes")) {
                runEditMenu = false;
            }
        }
    }

    public static void editEquipmentMenu() {
        System.out.println("Please choose one of the following options: ");
        System.out.println("""
                                1. Edit name.
                                2. Edit purchase date
                                3. Edit price.
                                4. Edit equipment state
                                5. Edit equipment type
                                6. Delete equipment
                                7. Quit menu""");

        System.out.print("Please enter the menu number: ");
    }

    public static void deleteEquipment(EquipmentRepo equipmentRepo, Integer id) {
        try {
            if (equipmentRepo.existsById(id)) {
                equipmentRepo.deleteById(id);
            } else {
                System.out.println("No equipment found with the given ID.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while deleting equipment: " + e.getMessage());
        }
    }

    public static void displayEssentialOfEquipment(EquipmentRepo equipmentRepo) {
        List<Equipment> equipmentList = equipmentRepo.findAll();
        if (equipmentList.isEmpty()) {
            System.out.println("No equipment available.");
        } else {
            for (Equipment equipment : equipmentList) {
                System.out.println("ID: " + equipment.getId() + ", Name: " + equipment.getName() + ", Type: " + equipment.getType());
            }
        }
    }

    public static void displayUnassignedEquipment (UnassignedEquipmentRepo unassignedEquipmentRepo){
        System.out.println(unassignedEquipmentRepo.findAll());
    }

    public static void displayLoggedInUsersEquipment(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser){
        List<Equipment> equipmentList = foundUser.getEquipmentList();
        if (!equipmentList.isEmpty()){
            for (Equipment tempList : equipmentList){
                System.out.println("Your are assigned a: " + tempList.getName());
            }
        } else {
            System.out.println("You have no active equipment.");
        }
    }

}


