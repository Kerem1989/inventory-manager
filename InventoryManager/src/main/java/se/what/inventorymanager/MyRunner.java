package se.what.inventorymanager;

import Utils.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    UserRepo userRepo;
    @Autowired
    EquipmentRepo equipmentRepo;
    @Autowired
    AssignedEquipmentRepo assignedEquipmentRepo;
    @Autowired
    EquipmentSupportRepo equipmentSupportRepo;
    @Autowired
    UnassignedEquipmentRepo unassignedEquipmentRepo;
    @Autowired
    EquipmentOrderRepo equipmentOrderRepo;


    Scanner input = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {

        int userchoice=0;
        do {
            InputOutput.introText();
            System.out.println("""
            Please choose option below:
            1 - Login
            2 - Exit Program""");
            int userChoice = InputOutput.getValidIntegerInput(input, 0, 3);

            switch (userChoice){
                case 2 -> {
                    System.out.println("thank you for using inventorymanager!");
                    System.exit(1);
                }
                case 1 -> InputOutput.login(userRepo, equipmentRepo, assignedEquipmentRepo, unassignedEquipmentRepo, equipmentSupportRepo, equipmentOrderRepo);
            }

        }while(userchoice!=2);

    }
}
