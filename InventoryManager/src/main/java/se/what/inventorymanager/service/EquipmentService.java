package se.what.inventorymanager.service;

import Utils.InputOutput;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        double inputpurchasePrice = InputOutput.getValidDoubleInput(input);
        equipment.setPurchasePrice(inputpurchasePrice);


        System.out.println("Please enter equipment type (\n1 - laptop\n2 - phone\n3 - screen):");

        int inputType = InputOutput.getValidIntegerInput(input, 1, 3);

        switch (inputType) {
            case 1 -> equipment.setType(EquipmentType.laptop);
            case 2 -> equipment.setType(EquipmentType.phone);
            case 3 -> equipment.setType(EquipmentType.screen);
        }

        System.out.println("Do you want to assign product on user?\n1 - yes\n2 - no");

        int assignToUserOrStock = InputOutput.getValidIntegerInput(input, 1, 2);

        boolean validUserIdInput = false;
        do {
            switch (assignToUserOrStock) {
                case 1 -> {
                    System.out.println(userRepo.findAll());
                    System.out.print("Please enter the ID of the user you want to assign equipment to: ");
                    int userIdChoice = InputOutput.getValidIntegerInput(input, 0, Integer.MAX_VALUE);
                    Optional<User> userOptional = null;
                    if (userRepo.existsUserById(userIdChoice)) {
                        userOptional = userRepo.findById(userIdChoice);
                        if (userOptional.isPresent()) {
                            User user = userOptional.get();
                            equipment.setUser(user);
                        }
                        equipment.setState(EquipmentState.assigned);
                        equipmentRepo.save(equipment);
                        System.out.println(equipment + " added");
                        validUserIdInput = true;

                    } else {
                        System.out.println("No user found with the given ID.");
                        validUserIdInput = false;
                    }
                }
                case 2 -> {
                    EquipmentState state = EquipmentState.unassigned;
                    equipment.setState(state);
                    equipmentRepo.save(equipment);
                    System.out.println(equipment + ". Added to stock");
                    validUserIdInput = true;
                }
            }
        } while (!validUserIdInput);
    }

    public static boolean displayEquipment(EquipmentRepo equipmentRepo) {
        System.out.println(equipmentRepo.findAll());
        return false;
    }

    public static void editEquipment(EquipmentRepo equipmentRepo) {
        boolean runEditMenu = true;
        while (runEditMenu) {
            System.out.println("Menu for editing equipment, please select an equipment id to begin editing: ");
            displayEssentialOfEquipment(equipmentRepo);

            System.out.println("Please enter the id of the equipment to begin editing:\nEnter '0' to abort");
            int selectEquipmentById = InputOutput.getValidIntegerInput(input,0, Integer.MAX_VALUE);

            Optional<Equipment> equipmentOptional = equipmentRepo.findById(selectEquipmentById);
            if (equipmentOptional.isPresent()) {
                boolean isEditingCurrentEquipment = true;
                while (isEditingCurrentEquipment) {
                    Equipment equipment = equipmentOptional.get();

                    int selectMenuOption = editEquipmentMenu();
                    switch (selectMenuOption) {
                        case 1 -> {
                            System.out.println("Enter the new name, type '0' to abort: ");
                            String editName = InputOutput.getValidStringInput(input);
                            if (editName.equals("0")){
                                isEditingCurrentEquipment = false;
                            }
                            equipment.setName(editName);
                            equipmentRepo.save(equipment);
                        }
                        case 2 -> {
                            System.out.println("Enter the new price, type 0 to abort: ");
                            int editPrice = InputOutput.getValidIntegerInput(input,0,Integer.MAX_VALUE);
                            if (editPrice==0){
                                isEditingCurrentEquipment = false;
                            }
                            equipment.setPurchasePrice(editPrice);
                            equipmentRepo.save(equipment);
                        }
                        case 3 -> {
                            System.out.println("Enter the new equipment state\n0 - Abort\n1 - assigned\n2 - unassigned: ");
                            int newState = InputOutput.getValidIntegerInput(input,1,2);

                            switch (newState){
                                case 0 -> isEditingCurrentEquipment = false;
                                case 1 -> equipment.setState(EquipmentState.assigned);
                                case 2 -> equipment.setState(EquipmentState.unassigned);
                            }
                            equipmentRepo.save(equipment);
                        }
                        case 4 -> {
                            System.out.println("Are you sure you want to delete " + equipment.getName() + "\n1 - Yes\n2 - No");

                            int confirmation = InputOutput.getValidIntegerInput(input,1,2);

                            if (confirmation==1) {
                                deleteEquipment(equipmentRepo, selectEquipmentById);
                                System.out.println("Equipment deleted successfully.");
                                isEditingCurrentEquipment = false;
                            } else {
                                System.out.println("Equipment deletion cancelled.");
                            }
                        }
                        case 0 -> isEditingCurrentEquipment = false;
                    }
                }
            } else {
                System.out.println("No equipment found with the given ID.");
            }
            System.out.println("Do you want to return to Equipment menu?\n1 - yes\n2 - No");
            int userDecision = InputOutput.getValidIntegerInput(input,1,2);

            if (userDecision == 1) {
                runEditMenu = false;
            }
        }
    }

    public static int editEquipmentMenu() {
        System.out.println("Please choose one of the following options: ");
        System.out.println("""
                0 - Quit menu
                1 - Edit name.
                2 - Edit price.
                3 - Edit equipment state
                4 - Delete equipment
                """);

        int menuChoice = InputOutput.getValidIntegerInput(input, 0, 4);

        return menuChoice;
    }

    public static void deleteEquipment(EquipmentRepo equipmentRepo, Integer id) {
        try {
            if (equipmentRepo.existsById(id)) {
                Equipment equipment = new Equipment();
                equipment.setState(EquipmentState.discontinued);
            } else {
                System.out.println("No equipment found with the given ID.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while discontinuing equipment: " + e.getMessage());
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

    public static void displayUnassignedEquipment(UnassignedEquipmentRepo unassignedEquipmentRepo) {
        System.out.println(unassignedEquipmentRepo.findAll());
    }

    public static void displayLoggedInUsersEquipment(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser) {
        List<Equipment> equipmentList = foundUser.getEquipmentList();
        if (!equipmentList.isEmpty()) {
            System.out.println("Your assigned equipment: ");
            for (Equipment tempList : equipmentList) {
                System.out.println("Id: " + tempList.getId() + " " + tempList.getName());
            }
        } else {
            System.out.println("You have no active equipment.");
        }
    }

}


