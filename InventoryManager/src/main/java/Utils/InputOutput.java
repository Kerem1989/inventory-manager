package Utils;

import se.what.inventorymanager.MyRunner;
import se.what.inventorymanager.RoleType;
import se.what.inventorymanager.UserRepo;
import se.what.inventorymanager.EquipmentRepo;
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
            if (!userInput.matches("[a-zA-ZåäöÅÄÖ0-9-@.]+")) {
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

    public static void login(UserRepo userRepo, EquipmentRepo equipmentRepo) {
        boolean runProgram = true;
        do {
            System.out.print("Please enter username: ");
            String username = getValidStringInput(input);
            System.out.print("Please enter password: ");
            String password = getValidStringInput(input);
            System.out.print("Please enter your role: ");
            String role = getValidStringInput(input);
            RoleType inputAsEnum = RoleType.valueOf(role);
            if (userRepo.existsUserByUsernameAndPassword(username, password) && (inputAsEnum == RoleType.admin)){
                    System.out.println("You are logged in as admin");
                    runProgram = false;
                }
            else if (userRepo.existsUserByUsernameAndPassword(username, password) && (inputAsEnum == RoleType.superuser)){
                System.out.println("You are logged in as superuser");
                runProgram = false;
            }
            else if (userRepo.existsUserByUsernameAndPassword(username, password) && (inputAsEnum == RoleType.user)){
                System.out.println("You are logged in as user");
                runProgram = false;
            }
            else {
                System.out.println("Incorrect username, password or role.");
                runProgram = false;
            }
            /*TODO: Här kanske man ska kalla på user-repot för att kontrollera om inloggningsuppgifterna stämmer?
            TODO: samt kontroll om användaren hämtar är admin, superUser eller user :)
            TODO: Eller ska inloggningslogiken ligga nån annanstans?
            * */

        } while (runProgram);
        menuAdmin(userRepo, equipmentRepo);

    }

    public static void menuAdmin(UserRepo userRepo, EquipmentRepo equipmentRepo) {
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
                case 1 -> manageUsersMenu(userRepo);
                case 2 -> manageEquipmentMenu(equipmentRepo, userRepo);
                case 3 -> manageSupportTicketMenu();

            }
        } while (menuOption != 0);
    }

    public void menuUser() {
        int menuOption;
        do {
            System.out.println("""
                    Choose option below:
                    0 - Logout
                    1 - Search available equipment in stock
                    2 - Display your equipment
                    3 - Support ticket
                    """);

            menuOption = getValidIntegerInput(input, 0, 3);

            switch (menuOption) {
                case 1 -> System.out.println("display!!!!");
                case 2 -> System.out.println("DISPLAY YOUR EQ");
                case 3 -> System.out.println("Create new/view your tickets");
            }
        } while (menuOption != 0);
    }

    private static void manageUsersMenu(UserRepo userRepo) {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Back to Main Menu
                    1 - Display all users
                    2 - Add User
                    3 - Edit User
                    4 - Remove User""");

            menuOption = getValidIntegerInput(input, 0, 4);

            switch (menuOption) {
                case 1 -> System.out.println("print all users..");
                case 2 -> MyRunner.addNewUser(userRepo);
                case 3 -> System.out.println("edit existing user");
                case 4 -> System.out.println("remove user? maybe should be under edit user?");
            }
        } while (menuOption != 0);
    }

    private static void manageEquipmentMenu(EquipmentRepo equipmentRepo, UserRepo userRepo) {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Back to Main Menu
                    1 - Add new Equipment to stock/user
                    2 - Edit equipment
                    3 - discard equipment
                    4 - Någonting mer kanske man vill göra?""");

            menuOption = getValidIntegerInput(input, 0, 4);

            switch (menuOption) {
                case 1 -> MyRunner.addNewEquipment(equipmentRepo, userRepo);
                case 2 -> System.out.println("HÄR REFERERAR MAN TILL REDIGERA UTRUSTNING-METODEN");
                case 3 -> System.out.println("HÄR REFERERAR MAN TILL TA BORT UTRUSTNING-METODEN");
                case 4 -> System.out.println("VILL MAN GÖRA NÅ ANNAT HÄR???");

            }

        } while (menuOption != 0);
    }

    public static void manageSupportTicketMenu() {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Back to Main Menu
                    1 - View all active support tickets
                    2 - Edit Support-Ticket
                    3 - 
                    4 - """);

            menuOption = getValidIntegerInput(input, 0, 4);

            switch (menuOption) {
                case 1 -> System.out.println("VIEW ALL ACTIVE SUPPORT-TICKETS");
                case 2 -> editSupportTicketMenu();
                case 3 -> System.out.println("SOME FUNCTIONALITY?");
                case 4 -> System.out.println("SOME FUNCTIONALITY?");
            }
        } while (menuOption != 0);
    }

    public static void editSupportTicketMenu() {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Back to Support-ticket Menu
                    1 - Change status of Support-ticket
                    2 - Close Support-ticket
                    3 - 
                    4 - """);

            menuOption = getValidIntegerInput(input, 0, 4);

            switch (menuOption) {
                case 1 -> System.out.println("VIEW ALL ACTIVE SUPPORT-TICKETS");
                case 2 -> System.out.println("EDIT TICKETS!");
                case 3 -> System.out.println("");

            }


        } while (menuOption != 0);
    }

    public static String getUserData(String s) {
        System.out.print(s);
        String adminInputName = InputOutput.getValidStringInput(input);
        return adminInputName;
    }


}