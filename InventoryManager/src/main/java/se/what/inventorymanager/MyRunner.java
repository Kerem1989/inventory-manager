package se.what.inventorymanager;
import Utils.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import static Utils.InputOutput.getValidIntegerInput;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    UserRepo userRepo;
    @Autowired
    EquipmentRepo equipmentRepo;
    Scanner input = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        InputOutput.introText();
        InputOutput.login(userRepo);


    }

    public static void addNewUser(UserRepo userRepo) {
        User user = new User();
        String adminInputName = InputOutput.getUserData("Please enter the users full name: ");
        user.setName(adminInputName);

        String adminInputDepartment = InputOutput.getUserData("Please enter the users department: ");
        user.setDepartment(adminInputDepartment);

        String adminInputEmail = InputOutput.getUserData("Please enter the users email: ");
        user.setEmail(adminInputEmail);

        String adminInputTelephone = InputOutput.getUserData("Please enter the users telephone number: ");
        user.setTelephone(adminInputTelephone);

        String adminInputUsername = InputOutput.getUserData("Please choose a username for the user: ");
        user.setUsername(adminInputUsername);

        String adminInputPassword = InputOutput.getUserData("Please choose a password for the user: ");
        user.setPassword(adminInputPassword);

        String adminInputRole = InputOutput.getUserData("Please enter a role for the user, must equal admin, superuser or user: ");
        if (adminInputRole.equalsIgnoreCase("admin")){
            user.setRole(RoleType.admin);
            userRepo.save(user);
            System.out.println("You have added " + user);
        }

        else if (adminInputRole.equalsIgnoreCase("superuser")){
            user.setRole(RoleType.superuser);
            userRepo.save(user);
            System.out.println("You have added " + user);
        }

        else if (adminInputRole.equalsIgnoreCase("user")){
            user.setRole(RoleType.user);
            userRepo.save(user);
            System.out.println("You have added " + user);
        }

        else {
            System.out.println("Wrong type of role for the user, please try again.");
        }


    }

    public static void addNewEquipment(EquipmentRepo equipmentRepo) {
        EquipmentService equipmentService = new EquipmentService();
        String name = InputOutput.getUserData("Please enter the equipment name: ");

        Date purchaseDate = InputOutput.asDate(LocalDate.now());

//        System.out.println("Please enter the equipment price: ");
//        double purchasePrice = InputOutput.getValidDoubleInput();

        EquipmentState state = EquipmentState.AVAILABLE;

        String inputType = InputOutput.getUserData("Please enter equipment type (LAPTOP, PHONE, MONITOR, PROJECTOR, OFFICE_CHAIR):");
        EquipmentType equipmentType = EquipmentType.fromString(inputType);
    }

    public static void editUser (UserRepo userRepo, Scanner input) {
        boolean runEditMenu = true;
        do {
            System.out.println("Welcome to the menu for editing user, please select a user id from the list below to begin editing:");
            System.out.println(userRepo.findAll());
            System.out.println("Please enter the id of the specific user to begin editing: ");
            int selectUserById = input.nextInt();
            input.nextLine();
            Optional<User> userOptional = userRepo.findById(selectUserById);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                System.out.println("Please choose from the following options to edit: ");
                System.out.println("1. Edit name." + "\n2. Edit department" + "\n3. Edit email." + "\n4. Edit telephone number" + "\n5. Edit username" + "\n6. Edit password." + "\n7. Edit role." + "\n8. Quit menu.");
                System.out.print("Please choose a option by entering the menu number: ");
                int selectMenuOption = getValidIntegerInput(input, 0, 8);
                switch (selectMenuOption) {
                    case 1 -> {
                        System.out.println("Enter the new name: ");
                        String editName = input.nextLine();
                        user.setName(editName);
                        userRepo.save(user);
                    }
                    case 2 -> {
                        System.out.println("Enter the new department: ");
                        String editDepartment = input.nextLine();
                        user.setDepartment(editDepartment);
                        userRepo.save(user);
                    }
                    case 3 -> {
                        System.out.println("Enter the new email: ");
                        String editEmail = input.nextLine();
                        user.setEmail(editEmail);
                        userRepo.save(user);
                    }
                    case 4 -> {
                        System.out.println("Enter the new telephone number: ");
                        String editTelephone = input.nextLine();
                        user.setTelephone(editTelephone);
                        userRepo.save(user);
                    }
                    case 5 -> {
                        System.out.println("Enter the new username: ");
                        String editUsername = input.nextLine();
                        user.setUsername(editUsername);
                        userRepo.save(user);
                    }
                    case 6 -> {
                        System.out.println("Enter the new password: ");
                        String editPassword = input.nextLine();
                        user.setPassword(editPassword);
                        userRepo.save(user);
                    }
                    case 7 -> {
                        System.out.println("Enter the new role ");
                        String editRole = input.nextLine();
                        if (editRole.equalsIgnoreCase("admin")) {
                            user.setRole(RoleType.admin);
                            userRepo.save(user);
                        } else if (editRole.equalsIgnoreCase("superuser")) {
                            user.setRole(RoleType.superuser);
                            userRepo.save(user);
                        } else if (editRole.equalsIgnoreCase("user")) {
                            user.setRole(RoleType.user);
                            userRepo.save(user);
                        } else {
                            System.out.println("Wrong type of role for the user, please try again.");
                        }
                    }
                    case 8 -> runEditMenu = false;

                }
            }
        } while (runEditMenu);
    }
}