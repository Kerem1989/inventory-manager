package se.what.inventorymanager.service;
import Utils.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.what.inventorymanager.domain.Equipment;
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
                         input, foundUser);
            }

        }while(userChoice != 0);
    }

    public static void displayLoggedInUsersTickets(EquipmentSupportRepo equipmentSupportRepo,EquipmentRepo equipmentRepo,UserRepo userRepo,User foundUser){
        List<Equipment> equipmentList = foundUser.getEquipmentList();
        if (!equipmentList.isEmpty()){
            for (Equipment tempList : equipmentList){
                EquipmentSupport equipmentSupport = tempList.getEquipmentSupport();
                System.out.println("Your tickets:" + "\n" + equipmentSupport);
            }
        } else {
            System.out.println("You have no active tickets.");
        }
    }

    public static void addTicket(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo,
                                 Scanner input,User foundUser) {

        EquipmentSupport equipmentSupport = new EquipmentSupport();
        equipmentSupport.setStatus(EquipmentStatus.open);
        equipmentSupport.setSupportRecord(1);


        System.out.println("Enter description of the problem for the ticket");
        String description = input.nextLine();
        equipmentSupport.setDescription(description);

        System.out.println("Enter the ID number of your borrowed equipment");
        int equipmentId = input.nextInt();
        input.nextLine();

        Optional<Equipment> optionalEquipment = equipmentRepo.findById(equipmentId);
        if (optionalEquipment.isPresent()) {
            Equipment equipment = optionalEquipment.get();
            equipmentSupport.setEquipment(equipment);
            equipmentSupportRepo.save(equipmentSupport);
            System.out.println("Support ticket created for " + equipmentId);
        } else {
            System.out.println("Not found");
        }
    }

    public static void oldTicketRetrieveChange(EquipmentSupportRepo equipmentSupportRepo,
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


    public static void deleteSupportTicket(EquipmentSupportRepo equipmentSupportRepo,
                                           Scanner input) {
        System.out.println("Provide the id number of your support ticket");
        int supportTicketId = input.nextInt();
        input.nextLine();
        if (equipmentSupportRepo.existsById(supportTicketId)) {
            equipmentSupportRepo.deleteById(supportTicketId);
            System.out.println("Support ticket removed");
        } else {
            System.out.println("No corresponding support ticket found");
        }
    }
}
