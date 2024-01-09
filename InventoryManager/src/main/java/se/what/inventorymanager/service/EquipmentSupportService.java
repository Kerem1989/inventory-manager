package se.what.inventorymanager.service;
import Utils.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.what.inventorymanager.domain.Equipment;
import se.what.inventorymanager.domain.SearchRecord;
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

    public static void displayLoggedInUsersTickets(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo, UserRepo userRepo, User foundUser) {

        foundUser = userRepo.getUserByUsernameAndPassword(foundUser.getUsername(), foundUser.getPassword());

        List<Equipment> equipmentList = foundUser.getEquipmentList();

        boolean hasSupportTickets = false;
        if (!equipmentList.isEmpty()) {
            for (Equipment tempList : equipmentList) {
                List<EquipmentSupport> equipmentSupportList = tempList.getEquipmentSupport();
                if (equipmentSupportList != null && !equipmentSupportList.isEmpty()) {
                    System.out.println("Tickets for Equipment ID " + tempList.getId() + ":");
                    for (EquipmentSupport equipmentSupport : equipmentSupportList) {
                        System.out.println(equipmentSupport);
                    }
                    hasSupportTickets = true;
                }
            }
            if (!hasSupportTickets) {
                System.out.println("You have no active support tickets\n");
            }
        } else {
            System.out.println("You have no assigned equipment");
        }
    }

    public static void addTicket(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo,
                                 Scanner input, UserRepo userRepo, User foundUser) {

        EquipmentSupport equipmentSupport = new EquipmentSupport();
        equipmentSupport.setStatus(EquipmentStatus.open);

        EquipmentService.displayLoggedInUsersEquipment(equipmentSupportRepo, equipmentRepo, userRepo, foundUser);

        boolean exit;

        do {
            System.out.println("Enter the ID of the product you want to create a ticket on: " +
                    "\nEnter '0' to exit\n");

            int equipmentId = InputOutput.getValidIntegerInput(input, 0, Integer.MAX_VALUE);

            if (equipmentId == 0) {
                exit = true;
            } else {

                Optional<Equipment> optionalEquipment = equipmentRepo.findById(equipmentId);

                if (optionalEquipment.isPresent()) {
                    Equipment equipment = optionalEquipment.get();
                    equipmentSupport.setEquipment(equipment);

                    if (equipment.getState().equals(EquipmentState.in_repair)) {
                        System.out.println("This equipment already has an active ticket..\n" +
                                "Please contact your manager or helpdesk for further information");
                        exit = true;
                    } else {

                        System.out.println("Enter a short description of the problem:\n");

                        String description = InputOutput.getValidStringInput(input);

                        equipmentSupport.setDescription(description);


                        int sumOfSupportRecords = equipmentSupport.getSupportRecord();
                        sumOfSupportRecords++;
                        equipmentSupport.setSupportRecord(sumOfSupportRecords);
                        equipmentSupportRepo.save(equipmentSupport);


                        int count = equipmentSupportRepo.countByEquipment(equipment);

                        equipment.setState(EquipmentState.in_repair);
                        equipmentSupport.setSupportRecord(count);
                        equipmentSupportRepo.save(equipmentSupport);
                        equipmentRepo.save(equipment);

                        System.out.println("Support ticket created for Equipment with id: " + equipmentId);
                        System.out.println("Total support records for this equipment: " + count);
                        exit = true;
                    }
                } else {
                    System.out.println("Unable to create a new support ticket...");
                    exit = false;
                }
            }
        } while (!exit);
    }


    public static void editSupportTicket(EquipmentSupportRepo equipmentSupportRepo,
                                         Scanner input) {
        System.out.println(equipmentSupportRepo.findAll());
        System.out.println("Provide the id number of your support ticket");
        int supportTicketId = InputOutput.getValidIntegerInput(input,0,Integer.MAX_VALUE);

        Optional<EquipmentSupport> optionalEquipmentSupport = equipmentSupportRepo.findById(supportTicketId);

        if (optionalEquipmentSupport.isPresent()) {
            EquipmentSupport equipmentSupport = optionalEquipmentSupport.get();
            System.out.println("Your support ticket was found");

            System.out.println("Change description of your ticket");
            String description = InputOutput.getValidStringInput(input);
            equipmentSupport.setDescription(description);

            System.out.println("Change status of your ticket (open/closed)");
            String statusInput = InputOutput.getValidStringInput(input);

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
        int foundId = InputOutput.getValidIntegerInput(input,0,Integer.MAX_VALUE);
        Optional<EquipmentSupport> equipmentSupportOptional = equipmentSupportRepo.findById(foundId);
        if (equipmentSupportOptional.isPresent()) {

            EquipmentSupport equipmentSupport = equipmentSupportOptional.get();
            equipmentSupport.setStatus(EquipmentStatus.closed);
            equipmentSupportRepo.save(equipmentSupport);
            Optional<Equipment> foundEquipmentOptional = equipmentRepo.findById(equipmentSupport.getEquipment().getId());

            if (foundEquipmentOptional.isPresent()) {
                Equipment foundEquipment = foundEquipmentOptional.get();

                foundEquipment.setState(EquipmentState.assigned);
                equipmentRepo.save(foundEquipment);
                System.out.println("Equipment status updated to assigned to user");
            } else {
                System.out.println("Associated equipment not found.");
            }
        } else {
            System.out.println("Support ticket not found.");
        }

    }
}