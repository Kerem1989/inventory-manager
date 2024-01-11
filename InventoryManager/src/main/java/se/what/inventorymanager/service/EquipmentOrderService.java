package se.what.inventorymanager.service;


import Utils.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.what.inventorymanager.domain.Equipment;
import se.what.inventorymanager.domain.EquipmentOrder;
import se.what.inventorymanager.domain.User;
import se.what.inventorymanager.enums.EquipmentType;
import se.what.inventorymanager.repository.EquipmentOrderRepo;
import se.what.inventorymanager.repository.EquipmentRepo;
import se.what.inventorymanager.repository.UserRepo;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static Utils.InputOutput.getValidDeliveryDate;
import static Utils.InputOutput.input;


@Service
public class EquipmentOrderService {

    @Autowired
    EquipmentOrderRepo equipmentOrderRepo;
    @Autowired
    EquipmentRepo equipmentRepo;
    @Autowired
    UserRepo userRepo;


    public static void equipmentOrderMenu(EquipmentOrderRepo equipmentOrderRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser) {
        int userChoice;
        do {

            System.out.println("""
                    please choose option below:
                    0 - Back to Admin menu
                    1 - Create new order
                    2 - Display all orders
                    3 - edit orders
                    """);

            userChoice = InputOutput.getValidIntegerInput(input, 0, 3);
            switch (userChoice) {
                case 1 -> createNewOrder(equipmentOrderRepo, equipmentRepo, userRepo, foundUser);
                case 2 -> printAllOrders(equipmentOrderRepo, equipmentRepo, userRepo, foundUser);
                case 3 -> editOrder(equipmentOrderRepo, equipmentRepo, userRepo, foundUser);
            }
        } while (userChoice != 0);

    }

    public static void createNewOrder(EquipmentOrderRepo equipmentOrderRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser) {
        EquipmentOrder equipmentOrder = new EquipmentOrder();
        equipmentOrder.setName(InputOutput.getUserDataString("Please enter name of equipment"));

        double orderPrice = InputOutput.getUserDataDouble("Please enter price of equipment");
        if (orderPrice<Integer.MAX_VALUE) {
            equipmentOrder.setPrice(orderPrice);
        }else {
            System.out.println("\nInvalid price! Please enter a price below: " + Integer.MAX_VALUE);
            return;
        }


        System.out.println("Enter a date (yyMMdd): ");

        Date estimatedDeliveryDate = getValidDeliveryDate();
        equipmentOrder.setEstDelDate(estimatedDeliveryDate);

        System.out.println("Please enter equipment type (\n1 - laptop\n2 - phone\n3 - screen):");

        int inputType = InputOutput.getValidIntegerInput(input, 1, 3);

        switch (inputType) {
            case 1 -> equipmentOrder.setType(EquipmentType.laptop);
            case 2 -> equipmentOrder.setType(EquipmentType.phone);
            case 3 -> equipmentOrder.setType(EquipmentType.screen);
        }

        equipmentOrder.setOrderDate(InputOutput.asDate(LocalDate.now()));
        equipmentOrder.setUser(foundUser);

        equipmentOrderRepo.save(equipmentOrder);


    }

    public static void editOrder(EquipmentOrderRepo equipmentOrderRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser) {


        System.out.println(equipmentOrderRepo.findAll());

        System.out.println("Please enter order ID of order you want to edit:");
        int foundId = InputOutput.getValidIntegerInput(input,0,Integer.MAX_VALUE);
        Optional<EquipmentOrder> equipmentOrderOptional = equipmentOrderRepo.findById(foundId);


        if (equipmentOrderOptional.isPresent()) {
            EquipmentOrder equipmentOrder = equipmentOrderOptional.get();

            System.out.println("""
                Please enter option below:
                0 - Back to admin menu
                1 - Change estimated delivery date
                2 - Remove order
                """);

            int userChoice = InputOutput.getValidIntegerInput(input, 0, 2);

            switch (userChoice) {
                case 1 -> changeDelDate(equipmentOrderRepo, equipmentRepo, userRepo, foundUser,equipmentOrder);
                case 2 -> removeOrder(equipmentOrderRepo, equipmentRepo, userRepo, foundUser,equipmentOrder);
            }
        }else{
            System.out.println("Unable to find order by that ID");
        }


    }

    public static void changeDelDate(EquipmentOrderRepo equipmentOrderRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser,EquipmentOrder equipmentOrder) {

        System.out.println("Please enter new delivery date (yyMMdd): ");

        Date estimatedDeliveryDate = getValidDeliveryDate();
        equipmentOrder.setEstDelDate(estimatedDeliveryDate);

        equipmentOrderRepo.save(equipmentOrder);
    }

    public static void removeOrder(EquipmentOrderRepo equipmentOrderRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser,EquipmentOrder equipmentOrder) {

        System.out.print("Are you sure you want to remove order with id: " + equipmentOrder.getId() +" from database?\n" +
                "1 - yes " +
                "2 - No, and return to menu" );

        int userChoice = InputOutput.getValidIntegerInput(input,1,2);
        switch (userChoice){
            case 1 -> {
                equipmentOrderRepo.delete(equipmentOrder);
                System.out.println("order " + equipmentOrder.getId() + " was removed");
                System.out.println("\nPlease add a " + equipmentOrder.getType() + "of model " +  equipmentOrder.getName() + " with price " + equipmentOrder.getPrice() + " kr " +  " to stock or user");
                EquipmentService.addNewEquipment(equipmentRepo, userRepo);
            }
            case 2 -> {
                System.out.println("returning to menu...");
                return;
            }
        }
    }

    public static void printAllOrders(EquipmentOrderRepo equipmentOrderRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser) {
        System.out.println(equipmentOrderRepo.findAll());
    }

}
