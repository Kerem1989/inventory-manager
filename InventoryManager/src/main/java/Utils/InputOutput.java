package Utils;

import se.what.inventorymanager.*;

import java.time.LocalDateTime;
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

    public static double getValidDoubleInput(Scanner input, double minValue) {

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

    public static String getValidStringInput(Scanner input) {
        String userInput;
        boolean isUserInputInvalid;

        do {
            userInput = input.nextLine();
            if (!userInput.matches("[a-zA-ZåäöÅÄÖ0-9@. ]+")) {
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

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
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
                             UnassignedEquipmentRepo unassignedEquipmentRepo, EquipmentSupportRepo equipmentSupportRepo) {
        boolean runProgram = true;
        do {
            System.out.print("Please enter username: ");
            String username = getValidStringInput(input);
            System.out.print("Please enter password: ");
            String password = getValidStringInput(input);

            User foundUser = userRepo.getUserByUsernameAndPassword(username,password);
            //foundUser får lov att vara inparameter så länge!
            //eller ska man skapa en dao så man inte skickar runt lösenord?

            if (foundUser==null){
                System.out.println("Incorrect username, password or role.");
                runProgram=true;
            }

            RoleType userRole = foundUser.getRole();

            if (userRole==RoleType.admin || userRole==RoleType.superuser){
                menuAdmin(userRepo, equipmentRepo, assignedEquipmentRepo, unassignedEquipmentRepo, equipmentSupportRepo);
                runProgram = false;
            } else {
                menuUser(userRepo);
                runProgram = false;
            }
        } while (runProgram);
    }


    public static void menuAdmin(UserRepo userRepo, EquipmentRepo equipmentRepo,
                                 AssignedEquipmentRepo assignedEquipmentRepo,
                                 UnassignedEquipmentRepo unassignedEquipmentRepo,
                                 EquipmentSupportRepo equipmentSupportRepo) {

        int menuOption;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Exit program
                    1 - Manage Users
                    2 - Manage Equipment
                    3 - Manage Support tickets""");

            menuOption = getValidIntegerInput(input, 0, 3);

            switch (menuOption) {
                case 0 -> System.out.println("Thank you for using Inventory-manager!");
                case 1 -> manageUsersMenu(userRepo, equipmentRepo);
                case 2 -> manageEquipmentMenu(equipmentRepo, assignedEquipmentRepo, unassignedEquipmentRepo);
                case 3 -> manageSupportTicketMenu(equipmentSupportRepo, equipmentRepo, input);

            }
        } while (menuOption != 0);
    }

    public static void menuUser(UserRepo userRepo) {
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
                case 1 -> System.out.println("display!!!!");
                case 2 -> System.out.println("DISPLAY YOUR EQ");
                case 3 -> System.out.println("Create new/view your tickets");
            }
        } while (menuOption != 0);
    }

    private static void manageUsersMenu(UserRepo userRepo, EquipmentRepo equipmentRepo) {
        int menuOption = 0;

        do {
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
        } while (menuOption != 0);
    }

    private static void manageEquipmentMenu(EquipmentRepo equipmentRepo, AssignedEquipmentRepo assignedEquipmentRepo, UnassignedEquipmentRepo unassignedEquipmentRepo) {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Back to Main Menu
                    1 - Display equipments
                    2 - Add new Equipment to stock/user
                    3 - Edit equipment""");

            menuOption = getValidIntegerInput(input, 0, 3);

            switch (menuOption) {
                case 1 -> manageDisplayEquipmentMenu(equipmentRepo, assignedEquipmentRepo, unassignedEquipmentRepo);
                case 2 -> System.out.println("REFERENS TILL LÄGGA TILL UTRUSTNING");
                case 3 -> System.out.println("HÄR REFERERAR MAN TILL REDIGERA UTRUSTNING-METODEN");
            }

        } while (menuOption != 0);
    }

    private static void manageDisplayEquipmentMenu (EquipmentRepo equipmentRepo, AssignedEquipmentRepo assignedEquipmentRepo, UnassignedEquipmentRepo unassignedEquipmentRepo) {
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

    public static void manageSupportTicketMenu(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo,
                                               Scanner input) {
        int menuOption = 0;
        do {
            System.out.println("""
                    Choose option below:
                    0 - Back to Main Menu
                    1 - Open a new support ticket
                    2 - View support tickets
                    3 - Edit support tickets
                    4 - Delete Support-Ticket""");


            menuOption = getValidIntegerInput(input, 0, 4);


            switch (menuOption) {
                case 1 -> addNewSupportTicket(equipmentSupportRepo, equipmentRepo, input);
                case 2 -> findAllEquipmentSupport(equipmentSupportRepo, input);
                case 3 -> editSupportTicketMenu(equipmentSupportRepo);
                case 4 -> deleteSupportTicket(equipmentSupportRepo, input);



            }
        } while (menuOption != 0);
    }

    private static void deleteSupportTicket(EquipmentSupportRepo equipmentSupportRepo, Scanner input) {
        EquipmentSupportService.deleteSupportTicket(equipmentSupportRepo, input);
    }


    private static void findAllEquipmentSupport(EquipmentSupportRepo equipmentSupportRepo,
                                                Scanner input) {
        System.out.println(equipmentSupportRepo.findAll());
    }


    private static void addNewSupportTicket(EquipmentSupportRepo equipmentSupportRepo, EquipmentRepo equipmentRepo,
                                            Scanner input) {
        int menuOption = 0;
        do {
            System.out.println("""
                Choose option below:
                0 - Back to Support-ticket Menu
                1 - Answer some questions so that your support ticket can be created
                2 - Retrieve old ticket and change info""");


            menuOption = getValidIntegerInput(input, 0, 2);
            switch (menuOption) {
                case 1 -> EquipmentSupportService.addTicket(equipmentSupportRepo, equipmentRepo, input);
                case 2 -> EquipmentSupportService.oldTicketRetrieveChange(equipmentSupportRepo, input);

            }
        } while (menuOption != 0);
    }


    public static void editSupportTicketMenu(EquipmentSupportRepo equipmentSupportRepo) {
        EquipmentSupportService.oldTicketRetrieveChange(equipmentSupportRepo, input);
    }

    public static void editUserSupportTicketMenu(EquipmentSupportRepo equipmentSupportRepo) {
        int menuOption = 0;
        do {
            System.out.println("""
                    Choose option below:
                    0 - Back to main menu
                    1 - Create a new support ticket
                    2 - View your support ticket""");
            menuOption = getValidIntegerInput(input, 0, 2);
            switch (menuOption) {
                case 1 -> System.out.println("create new support ticket");
                case 2 -> System.out.println("view your support ticket");
            }

        } while (menuOption != 0);
    }


    public static String getUserData(String s) {
        System.out.print(s);
        String adminInputName = InputOutput.getValidStringInput(input);
        return adminInputName;
    }
}