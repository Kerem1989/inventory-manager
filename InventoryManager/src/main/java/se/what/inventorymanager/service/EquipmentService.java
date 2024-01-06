package se.what.inventorymanager.service;

import Utils.InputOutput;
import Utils.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.what.inventorymanager.Equipment;
import se.what.inventorymanager.EquipmentState;
import se.what.inventorymanager.EquipmentType;
import se.what.inventorymanager.User;
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
        System.out.println("Please enter name of the equipment: ");
        String inputName = UserInput.readString();
        equipment.setName(inputName);

        Date purchaseDate = InputOutput.asDate(LocalDate.now());
        equipment.setPurchaseDate(purchaseDate);

        System.out.print("Please enter price of the equipment: ");
        int inputpurchasePrice = UserInput.readInt();
        equipment.setPurchasePrice(inputpurchasePrice);

        EquipmentState state = EquipmentState.unassigned;
        equipment.setState(state);

        boolean validType = false;
        EquipmentType type = null;
        while (!validType) {
            System.out.println("Please enter equipment type (laptop, screen, phone):");
            String inputType = UserInput.readString();
            type = EquipmentType.fromString(inputType);
            if (type != null) {
                validType = true;
            } else {
                System.out.println("Invalid equipment type. Please enter a valid type.");
            }
        }
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
            System.out.println(equipmentRepo.findAll());
            System.out.println("Please enter the id of the equipment to begin editing: ");
            int selectEquipmentById = input.nextInt();
            input.nextLine();
            Optional<Equipment> equipmentOptional = equipmentRepo.findById(selectEquipmentById);

            if (equipmentOptional.isPresent()) {
                boolean isEditingCurrentEquipment = true;
                while (isEditingCurrentEquipment) {
                    Equipment equipment = equipmentOptional.get();
                    System.out.println("Please choose one of the following options: ");
                    System.out.println("1. Edit name." + "\n2. Edit purchase date" + "\n3. Edit price."
                            + "\n4. Edit equipment state" + "\n5. Edit equipment type" + "\n6. Quit menu");

                    System.out.print("Please enter the menu number: ");
                    int selectMenuOption = getValidIntegerInput(input, 0, 6);
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
                        case 6 -> isEditingCurrentEquipment = false;
                    }}
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

    public void deleteEquipment(Integer id) {
        equipmentRepo.deleteById(id);
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
