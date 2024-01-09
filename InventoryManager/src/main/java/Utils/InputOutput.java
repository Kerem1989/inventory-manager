package Utils;

import se.what.inventorymanager.domain.Equipment;
import se.what.inventorymanager.domain.SearchRecord;
import se.what.inventorymanager.domain.User;
import se.what.inventorymanager.enums.RoleType;
import se.what.inventorymanager.repository.*;
import se.what.inventorymanager.service.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;


public class InputOutput {
    public static Scanner input = new Scanner(System.in);

    public static int getValidIntegerInput(Scanner input, int minValue, int maxValue) {
        int userInput = 0;
        boolean isUserInputInvalid;

        do {
            isUserInputInvalid = false;
            try {
                userInput = input.nextInt();
                if (userInput < minValue || userInput > maxValue) {
                    System.out.println("Invalid entry, please enter a number between " + minValue + " and " + maxValue + "...");
                    isUserInputInvalid = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid entry, please enter a number between " + minValue + " and " + maxValue + "...");
                isUserInputInvalid = true;
            }
            input.nextLine();
        } while (isUserInputInvalid);

        return userInput;
    }

    public static double getValidDoubleInput(Scanner input) {
        double minValue = 0.0;
        double userInput = 0.0;
        boolean isUserInputInvalid;

        do {
            isUserInputInvalid = false;
            try {
                String inputString = input.nextLine().replace(',', '.');
                userInput = Double.parseDouble(inputString);

                if (userInput < minValue) {
                    System.out.println("Incorrect value, please enter a value larger than " + minValue + "...");
                    isUserInputInvalid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Incorrect value, please enter a valid price: ");
                isUserInputInvalid = true;
            }

        } while (isUserInputInvalid);

        return userInput;
    }

    public static Date getValidDate(String inputDate) {
        boolean isUserInputInvalid;

        Date date = null;
        do {
            isUserInputInvalid = false;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
                date = sdf.parse(inputDate);
                System.out.println(date);

            } catch (Exception e) {
                System.out.println("invalid input");
            }
        } while (isUserInputInvalid);
        return date;
    }

    public static Date getValidDeliveryDate() {
        Date date = null;
        do {
            try {
                String dateStr = input.nextLine();
                date = InputOutput.getValidDate(dateStr);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid date.");
            }
        } while (date == null);

        return date;
    }

    public static String getValidStringInput(Scanner input) {
        String userInput;
        boolean isUserInputInvalid;

        do {
            userInput = input.nextLine();
            if (!userInput.matches("[-a-zA-ZåäöÅÄÖ0-9@._ ]+")) {
                System.out.println("Incorrect format, you cannot use special characters!");
                isUserInputInvalid = true;
            } else if (userInput.isEmpty()) {
                System.out.println("entry cannot be blank..");
                isUserInputInvalid = true;
            } else {
                isUserInputInvalid = false;
            }

        } while (isUserInputInvalid);

        return userInput;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static void introText() {
        System.out.println("Welcome to");
        System.out.println("""
                 _                      _                                                           \s
                (_)_ ____   _____ _ __ | |_ ___  _ __ _   _  /\\/\\   __ _ _ __   __ _  __ _  ___ _ __\s
                | | '_ \\ \\ / / _ \\ '_ \\| __/ _ \\| '__| | | |/    \\ / _` | '_ \\ / _` |/ _` |/ _ \\ '__|
                | | | | \\ V /  __/ | | | || (_) | |  | |_| / /\\/\\ \\ (_| | | | | (_| | (_| |  __/ |  \s
                |_|_| |_|\\_/ \\___|_| |_|\\__\\___/|_|   \\__, \\/    \\/\\__,_|_| |_|\\__,_|\\__, |\\___|_|  \s
                                                      |___/                          |___/          \s
                """);
    }

    public static void login(UserRepo userRepo, EquipmentRepo equipmentRepo,
                             AssignedEquipmentRepo assignedEquipmentRepo,
                             UnassignedEquipmentRepo unassignedEquipmentRepo, EquipmentSupportRepo equipmentSupportRepo, EquipmentOrderRepo equipmentOrderRepo, SearchRecordRepo searchRecordRepo) {
        boolean runProgram = true;
        do {
            System.out.print("Please enter username: ");
            String username = getValidStringInput(input);
            System.out.print("Please enter password: ");
            String password = getValidStringInput(input);

            User foundUser = userRepo.getUserByUsernameAndPassword(username, password);

            if (foundUser == null) {
                System.out.println("Incorrect username, password");
                runProgram = true;
            }else {

                RoleType userRole = foundUser.getRole();
                if (userRole == RoleType.admin || userRole == RoleType.superuser) {
                    menuAdmin(userRepo, equipmentRepo, assignedEquipmentRepo, unassignedEquipmentRepo, equipmentSupportRepo, foundUser, equipmentOrderRepo, searchRecordRepo);
                    runProgram = false;
                } else {
                    menuUser(userRepo, unassignedEquipmentRepo, foundUser, equipmentRepo, equipmentSupportRepo, searchRecordRepo);
                    runProgram = false;
                }
            }

        } while (runProgram);
    }

    public static void menuAdmin(UserRepo userRepo, EquipmentRepo equipmentRepo,
                                 AssignedEquipmentRepo assignedEquipmentRepo,
                                 UnassignedEquipmentRepo unassignedEquipmentRepo,
                                 EquipmentSupportRepo equipmentSupportRepo, User foundUser, EquipmentOrderRepo equipmentOrderRepo,SearchRecordRepo searchRecordRepo) {

        int menuOption;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Logout
                    1 - Manage Users
                    2 - Manage Equipment
                    3 - Manage Support tickets
                    4 - Manage orders
                    5 - View search records""");

            menuOption = getValidIntegerInput(input, 0, 5);


            switch (menuOption) {

                case 1 -> manageUsersMenu(userRepo, equipmentRepo,foundUser);
                case 2 -> manageEquipmentMenu(equipmentRepo, assignedEquipmentRepo, unassignedEquipmentRepo, userRepo);
                case 3 -> EquipmentSupportService.manageSupportTicketMenu(equipmentSupportRepo, equipmentRepo, input,userRepo,foundUser);
                case 4 -> EquipmentOrderService.equipmentOrderMenu(equipmentOrderRepo, equipmentRepo, userRepo, foundUser);
                case 5 -> SearchRecordService.searchRecordMenu(searchRecordRepo);

            }
        } while (menuOption != 0);
    }

    public static void menuUser(UserRepo userRepo, UnassignedEquipmentRepo unassignedEquipmentRepo, User foundUser, EquipmentRepo equipmentRepo,EquipmentSupportRepo equipmentSupportRepo,SearchRecordRepo searchRecordRepo) {
        int menuOption;
        do {
            System.out.println("""
                    Choose option below:
                    0 - Logout
                    1 - Search available equipment in stock
                    2 - Display your equipment
                    3 - Support ticket""");

            menuOption = getValidIntegerInput(input, 0, 3);

            switch (menuOption) {
                case 1 -> SearchRecordService.equipmentSearchQueryMenu(userRepo, unassignedEquipmentRepo,  foundUser,  equipmentRepo, equipmentSupportRepo, searchRecordRepo);
                case 2 -> EquipmentService.displayLoggedInUsersEquipment(equipmentSupportRepo,equipmentRepo, userRepo,foundUser);
                case 3 -> EquipmentSupportService.userSupportMenu( equipmentSupportRepo, equipmentRepo,
                        input, userRepo, foundUser);
            }
        } while (menuOption != 0);
    }

    private static void manageUsersMenu(UserRepo userRepo, EquipmentRepo equipmentRepo, User foundUser) {
        int menuOption = 0;

//        Boolean isValidUser = true;

        do {
            if (foundUser.getRole().equals(RoleType.superuser)){
                System.out.println("Unauthorized access, only Admin can edit users...\n");
//                isValidUser = false;
                return;
            }else {


                System.out.println("""
                        Choose option below:
                        0 - Back to Main Menu
                        1 - Display all users
                        2 - Add User
                        3 - Edit User""");

                menuOption = getValidIntegerInput(input, 0, 3);

                switch (menuOption) {
                    case 1 -> UserService.findAllUsers(userRepo, input);
                    case 2 -> UserService.addNewUser(userRepo);
                    case 3 -> UserService.editUser(userRepo, input, equipmentRepo, new Equipment());
                }

            }
        } while (menuOption != 0);
    }

    private static void manageEquipmentMenu(EquipmentRepo equipmentRepo, AssignedEquipmentRepo assignedEquipmentRepo, UnassignedEquipmentRepo unassignedEquipmentRepo, UserRepo userRepo) {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Back to Main Menu
                    1 - Display equipments
                    2 - Add new Equipment to stock/user
                    3 - Edit equipment
                    """);

            menuOption = getValidIntegerInput(input, 0, 3);

            switch (menuOption) {
                case 1 -> adminDisplayEquipmentMenu(equipmentRepo, assignedEquipmentRepo, unassignedEquipmentRepo);
                case 2 -> EquipmentService.addNewEquipment(equipmentRepo, userRepo);
                case 3 -> EquipmentService.editEquipment (equipmentRepo);
            }
        } while (menuOption != 0);
    }

    private static void adminDisplayEquipmentMenu(EquipmentRepo equipmentRepo, AssignedEquipmentRepo assignedEquipmentRepo, UnassignedEquipmentRepo unassignedEquipmentRepo) {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Back to Main Menu
                    1 - Display all equipments
                    2 - Display all unassigned equipments
                    3 - Display assigned equipments""");

            menuOption = getValidIntegerInput(input, 0, 3);

            switch (menuOption) {
                case 1 -> System.out.println(EquipmentService.displayEquipment(equipmentRepo));
                case 2 -> EquipmentService.displayUnassignedEquipment(unassignedEquipmentRepo);
                case 3 -> UserService.displayEquipmentOwner(assignedEquipmentRepo);
            }
        } while (menuOption != 0);
    }

    public static String getUserDataString(String s) {
        System.out.print(s);
        String adminInputName = InputOutput.getValidStringInput(input);
        return adminInputName;
    }

    public static Double getUserDataDouble(String s) {
        System.out.print(s);
        Double adminInputName = InputOutput.getValidDoubleInput(input);
        return adminInputName;
    }

    public static int getUserDataInteger(String s) {
        System.out.print(s);
        int adminInputName = InputOutput.getValidIntegerInput(input, 0, Integer.MAX_VALUE);
        return adminInputName;
    }

}