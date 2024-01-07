package se.what.inventorymanager.service;
import Utils.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.what.inventorymanager.domain.Equipment;
import se.what.inventorymanager.enums.EquipmentState;
import se.what.inventorymanager.enums.EquipmentStatus;
import se.what.inventorymanager.domain.EquipmentSupport;
import se.what.inventorymanager.domain.User;
import se.what.inventorymanager.repository.EquipmentRepo;
import se.what.inventorymanager.repository.EquipmentSupportRepo;
import se.what.inventorymanager.repository.UserRepo;


import java.util.List;
import java.util.Optional;
import java.util.Scanner;




@Service
public class EquipmentSupportService {

    @Autowired
    EquipmentSupportRepo equipmentSupportRepo;
    @Autowired
    EquipmentRepo equipmentRepo;
    @Autowired
    UserRepo userRepo;



    public static void userSupportMenu(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo,
                                 Scanner input, UserRepo userRepo, User foundUser) {
        int userChoice = 0;
        do {
            System.out.println("""
                    Please choose option below:
                    0 - Back to main menu
                    1 - View your tickets
                    2 - create a new support ticket
                    """);

            userChoice = InputOutput.getValidIntegerInput(input,0,2);

            switch (userChoice){
                case 1 -> displayLoggedInUsersTickets(equipmentSupportRepo,equipmentRepo, userRepo,foundUser);
                case 2 -> addTicket( equipmentSupportRepo,  equipmentRepo,
                         input, userRepo,foundUser);
            }

        }while(userChoice != 0);
    }



    public static void manageSupportTicketMenu(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo,
                                               Scanner input,UserRepo userRepo, User foundUser) {
        int menuOption = 0;
        do {
            System.out.println("""
                    Choose option below:
                    0 - Back to Main Menu
                    1 - Open a new support ticket
                    2 - View support tickets
                    3 - Edit support tickets
                    4 - Delete Support-Ticket""");


            menuOption = InputOutput.getValidIntegerInput(input, 0, 4);


            switch (menuOption) {
                case 1 -> addTicket(equipmentSupportRepo, equipmentRepo, input, userRepo,foundUser);
                case 2 -> System.out.println(equipmentSupportRepo.findAll());
                case 3 -> editSupportTicket(equipmentSupportRepo,input);
                case 4 -> deleteSupportTicket(equipmentSupportRepo, equipmentRepo, input);


            }
        } while (menuOption != 0);
    }

    public static void displayLoggedInUsersTickets(EquipmentSupportRepo equipmentSupportRepo,EquipmentRepo equipmentRepo,UserRepo userRepo,User foundUser){
        List<Equipment> equipmentList = foundUser.getEquipmentList();
        boolean hasSupportTickets = false;
        if (!equipmentList.isEmpty()){
            for (Equipment tempList : equipmentList){
                EquipmentSupport equipmentSupport = tempList.getEquipmentSupport();
                if (tempList.getEquipmentSupport() != null){
                    System.out.println("Your tickets:" + "\n" + equipmentSupport);
                    hasSupportTickets = true;
                }
            }
            if (!hasSupportTickets){
                System.out.println("You have no active support tickets\n");
            }
        }else {
            System.out.println("you have no assigned equipment");
        }
    }

    public static void addTicket(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo,
                                 Scanner input, UserRepo userRepo,User foundUser) {

        EquipmentSupport equipmentSupport = new EquipmentSupport();
        equipmentSupport.setStatus(EquipmentStatus.open);
        equipmentSupport.setSupportRecord(1);

        EquipmentService.displayLoggedInUsersEquipment(equipmentSupportRepo, equipmentRepo, userRepo, foundUser);

        boolean exit;

        do {
            System.out.println("Enter the ID of the product you want to create a ticket on: " +
                    "\nEnter '0' to exit\n");

            int equipmentId = InputOutput.getValidIntegerInput(input,0,Integer.MAX_VALUE);

            if (equipmentId==0){
                exit = true;
            }else {

                System.out.println("Enter a short description of the problem:\n");

                String description = InputOutput.getValidStringInput(input);

                equipmentSupport.setDescription(description);

                Optional<Equipment> optionalEquipment = equipmentRepo.findById(equipmentId);


                if (optionalEquipment.isPresent()) {
                    Equipment equipment = optionalEquipment.get();
                    equipmentSupport.setEquipment(equipment);
                    equipment.setState(EquipmentState.in_repair);

                    equipmentSupportRepo.save(equipmentSupport);
                    equipmentRepo.save(equipment);

                    System.out.println("Support ticket created for " + equipmentId);
                    exit = true;
                } else {
                    System.out.println("unable to create new support ticket...");
                    exit = false;
                }
            }
        } while (!exit);
    }


    public static void editSupportTicket(EquipmentSupportRepo equipmentSupportRepo,
                                         Scanner input) {
        System.out.println("Provide the id number of your support ticket");
        int supportTicketId = input.nextInt();
        input.nextLine();

        Optional<EquipmentSupport> optionalEquipmentSupport = equipmentSupportRepo.findById(supportTicketId);

        if (optionalEquipmentSupport.isPresent()) {
            EquipmentSupport equipmentSupport = optionalEquipmentSupport.get();
            System.out.println("Your support ticket was found");

            System.out.println("Change description of your ticket");
            String description = input.nextLine();
            equipmentSupport.setDescription(description);

            System.out.println("Change status of your ticket (open/closed)");
            String statusInput = input.nextLine();

            EquipmentStatus newStatus = EquipmentStatus.valueOf(statusInput.toLowerCase());
            equipmentSupport.setStatus(newStatus);

            equipmentSupportRepo.save(equipmentSupport);
            System.out.println("Support ticket updated");
        } else {
            System.out.println("We could not find your support ticket");


        }
    }


    public static void deleteSupportTicket(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo,
                                           Scanner input) {

        System.out.println(equipmentSupportRepo.findAll());

        System.out.println("Please enter support ticket nr. you want to delete:");
        int foundId = input.nextInt();
        input.nextLine();
        Optional<EquipmentSupport> equipmentSupportOptional = equipmentSupportRepo.findById(foundId);
        if (equipmentSupportOptional.isPresent()) {

            EquipmentSupport equipmentSupport = equipmentSupportOptional.get();
            equipmentSupportRepo.delete(equipmentSupport);
            Optional<Equipment> foundEquipmentOptional = equipmentRepo.findById(equipmentSupport.getEquipment().getId());

            if (foundEquipmentOptional.isPresent()) {
                Equipment foundEquipment = foundEquipmentOptional.get();
                foundEquipment.setState(EquipmentState.assigned);
                equipmentRepo.save(foundEquipment);
                System.out.println("Equipment status updated to unassigned (i.e. available).");
            } else {
                System.out.println("Associated equipment not found.");
            }
        } else {
            System.out.println("Support ticket not found.");

        }


    }
}