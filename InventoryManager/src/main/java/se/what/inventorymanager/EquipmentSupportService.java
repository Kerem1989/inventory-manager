package se.what.inventorymanager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.Scanner;




@Service
public class EquipmentSupportService {

    @Autowired
    EquipmentSupportRepo equipmentSupportRepo;
    @Autowired
    EquipmentRepo equipmentRepo;



    public static void addTicket(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo,
                                 Scanner input) {
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
