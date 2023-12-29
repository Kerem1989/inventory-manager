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

    Scanner input = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        System.out.println(equipmentSupportRepo.findAll());
        InputOutput.introText();
        InputOutput.login(userRepo, equipmentRepo, assignedEquipmentRepo);
    }
}
