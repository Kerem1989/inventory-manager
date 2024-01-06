package se.what.inventorymanager.service;


import Utils.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.what.inventorymanager.domain.EquipmentOrder;
import se.what.inventorymanager.domain.User;
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

        equipmentOrder.setPrice(InputOutput.getUserDataDouble("Please enter price of equipment"));

        System.out.println("Enter a date (dd/mm/yyyy): ");

        Date estimatedDeliveryDate = getValidDeliveryDate();
        equipmentOrder.setEstDelDate(estimatedDeliveryDate);

        equipmentOrder.setOrderDate(InputOutput.asDate(LocalDate.now()));
        equipmentOrder.setUser(foundUser);

        equipmentOrderRepo.save(equipmentOrder);


    }

    public static void editOrder(EquipmentOrderRepo equipmentOrderRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser) {


        System.out.println(equipmentOrderRepo.findAll());

        InputOutput.getUserData("Please enter order ID of order you want to edit:");
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
            InputOutput.getUserData("Unable to find order by that ID");
        }


    }

    public static void changeDelDate(EquipmentOrderRepo equipmentOrderRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser,EquipmentOrder equipmentOrder) {

        System.out.println("Please enter new delivery date (yyMMdd): ");

        Date estimatedDeliveryDate = getValidDeliveryDate();
        equipmentOrder.setEstDelDate(estimatedDeliveryDate);

        equipmentOrderRepo.save(equipmentOrder);
    }

    public static void removeOrder(EquipmentOrderRepo equipmentOrderRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser,EquipmentOrder equipmentOrder) {

        System.out.println("Are you sure you want to remove order with id: " + equipmentOrder.getId() +" from datebase?\n" +
                "1 - yes" +
                "2 - No, and return to menu");

        int userChoice = InputOutput.getValidIntegerInput(input,1,2);
        switch (userChoice){
            case 1 -> {
                equipmentOrderRepo.delete(equipmentOrder);
                System.out.println("order " + equipmentOrder.getId() + " was removed");
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
