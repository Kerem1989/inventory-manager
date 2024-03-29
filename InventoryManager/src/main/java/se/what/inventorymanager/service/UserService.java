package se.what.inventorymanager.service;

import Utils.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.what.inventorymanager.domain.Equipment;
import se.what.inventorymanager.enums.EquipmentState;
import se.what.inventorymanager.enums.RoleType;
import se.what.inventorymanager.domain.User;
import se.what.inventorymanager.repository.AssignedEquipmentRepo;
import se.what.inventorymanager.repository.EquipmentRepo;
import se.what.inventorymanager.repository.UserRepo;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static Utils.InputOutput.*;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AssignedEquipmentRepo assignedEquipmentRepo;

    @Autowired
    EquipmentRepo equipmentRepo;

    public static void addNewUser(UserRepo userRepo) {
        User user = new User();
        String adminInputName = InputOutput.getUserDataString("Please enter the users full name: ");
        user.setName(adminInputName);

        String adminInputDepartment = InputOutput.getUserDataString("Please enter the users department: ");
        user.setDepartment(adminInputDepartment);

        String adminInputEmail = InputOutput.getUserDataString("Please enter the users email: ");
        user.setEmail(adminInputEmail);

        String adminInputTelephone = InputOutput.getUserDataString("Please enter the users telephone number: ");
        user.setTelephone(adminInputTelephone);

        String adminInputUsername = InputOutput.getUserDataString("Please choose a username for the user: ");
        user.setUsername(adminInputUsername);

        String adminInputPassword = InputOutput.getUserDataString("Please choose a password for the user: ");
        user.setPassword(adminInputPassword);

        System.out.println("Please select the user role from the following numerical options: " +
                "\n1. Admin" +
                "\n2. Superuser" +
                "\n3. User");
        int editRole = InputOutput.getValidIntegerInput(input, 0, 3);
        switch (editRole) {
            case 1 -> {
                user.setRole(RoleType.admin);
                userRepo.save(user);
                System.out.println("You added: " + user);
            }
            case 2 -> {
                user.setRole(RoleType.superuser);
                userRepo.save(user);
                System.out.println("You added: " + user);
            }
            case 3 -> {
                user.setRole(RoleType.user);
                userRepo.save(user);
                System.out.println("You added: " + user);
            }
        }

    }

    public static void editUser(UserRepo userRepo, Scanner input, EquipmentRepo equipmentRepo, Equipment equipment) {
        boolean runEditMenu = true;
        do {
            System.out.println("Welcome to the menu for editing user, please select a user id from the list below to begin editing:");
            System.out.println(userRepo.findAll());
            System.out.println("");
            System.out.print("Please enter the id of the specific user to begin editing\n" +
                    "Enter '0' to exit: ");
            int selectUserById = getValidIntegerInput(input, 0, Integer.MAX_VALUE);
            if (selectUserById == 0) {
                return;
            } else {

                Optional<User> userOptional = userRepo.findById(selectUserById);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    System.out.println("Please choose from the following options to edit: ");
                    System.out.println("1. Edit name." + "\n2. Edit department" + "\n3. Edit email." + "\n4. Edit telephone number" +
                            "\n5. Edit username" + "\n6. Edit password." + "\n7. Edit role." + "\n8. Remove user." + "\n9. Quit menu.");
                    System.out.print("Please choose a option by entering the menu number: ");
                    int selectMenuOption = getValidIntegerInput(input, 0, 9);
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
                            System.out.println("Please select the new user role from the following numerical options:" +
                                    "\n1. Admin" +
                                    "\n2. Superuser" +
                                    "\n3. User");
                            int editRole = InputOutput.getValidIntegerInput(input, 0, 3);
                            switch (editRole) {
                                case 1 -> {
                                    user.setRole(RoleType.admin);
                                    userRepo.save(user);
                                    System.out.println("You added: " + user);
                                }
                                case 2 -> {
                                    user.setRole(RoleType.superuser);
                                    userRepo.save(user);
                                    System.out.println("You added: " + user);
                                }
                                case 3 -> {
                                    user.setRole(RoleType.user);
                                    userRepo.save(user);
                                    System.out.println("You added: " + user);
                                }
                            }
                        }
                        case 8 -> {
                            System.out.println("Please enter the id of the user you want to remove: ");
                            int deleteUserById = input.nextInt();
                            input.nextLine();
                            if (userRepo.existsUserById(deleteUserById)) {
                                Optional<User> deletedUser = userRepo.findById(deleteUserById);
                                if (deletedUser.isPresent()) {
                                    List<Equipment> equipmentList = deletedUser.get().getEquipmentList();
                                    if (!equipmentList.isEmpty()) {
                                        for (Equipment assignedEquipment : equipmentList) {
                                            assignedEquipment.setState(EquipmentState.unassigned);
                                            equipmentRepo.save(assignedEquipment);
                                        }
                                    } else {
                                        System.out.println("No equipment found for the user, deletion of user progressing");
                                    }

                                    userRepo.deleteById(deleteUserById);
                                    System.out.println("User has been successfully deleted");
                                } else {
                                    System.out.println("No user found for the given ID.");
                                }
                            } else {
                                System.out.println("No deletion occurred, user ID not found.");
                            }

                        }
                        case 9 -> runEditMenu = false;
                    }
                } else {
                    System.out.println("No user with the selected ID found.");
                    System.out.println("");
                }
            }
        } while (runEditMenu);
    }

    public static void findAllUsers(UserRepo userRepo, Scanner input) {
        System.out.println(userRepo.findAll());
        System.out.println("");
    }

    public static void displayEquipmentOwner(AssignedEquipmentRepo assignedEquipmentRepo) {
        System.out.println(assignedEquipmentRepo.findAll());
    }
}
