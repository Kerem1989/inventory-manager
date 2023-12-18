package se.what.inventorymanager;
import java.util.Scanner;

public class InputOutput {
    public static Scanner input = new Scanner(System.in);

    public int getValidIntegerInput(Scanner input, int minValue, int maxValue) {
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

    public static String getValidStringInput(Scanner input) {
        String userInput;
        boolean isUserInputInvalid;

        do {
            userInput = input.nextLine();
            if (!userInput.matches("[a-zA-ZåäöÅÄÖ0-9]+")) {
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

    public String introText() {
        return "VÄLKOME FRIENDO!";
    }

    public static void login() {
        do {
            System.out.println("Please enter username:");
            String userName = getValidStringInput(input);
            System.out.println("Please enter password");
            String password = getValidStringInput(input);

            /*TODO: Här kanske man ska kalla på user-repot för att kontrollera om inloggningsuppgifterna stämmer?
            TODO: samt kontroll om användaren hämtar är admin, superUser eller user :)
            * */

        } while (true);
    }

    public void menuAdmin() {
        int menuOption;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Logout
                    1 - Manage Users
                    2 - Manage Equipment
                    3 - Manage Support tickets""");

            menuOption = getValidIntegerInput(input, 0, 3);

            switch (menuOption) {
                case 0 -> System.out.println("logga ut");
                case 1 -> manageUsersMenu();
                case 2 -> manageEquipment();
                case 3 -> manageSupportTicket();

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

    private void manageUsersMenu() {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Logout
                    1 - Display all users
                    2 - Add User
                    3 - Edit User
                    4 - Remove User""");

            menuOption = getValidIntegerInput(input, 0, 4);

            switch (menuOption) {
                case 1 -> System.out.println("print all users..");
                case 2 -> System.out.println("add new user");
                case 3 -> System.out.println("edit existing user");
                case 4 -> System.out.println("remove user? maybe should be under edit user?");
            }
        } while (menuOption != 0);

    }

    private void manageEquipment() {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    1 - Add new Equipment to stock/user
                    2 - Edit equipment
                    3 - discard equipment
                    4 - Någonting mer kanske man vill göra?""");

            menuOption = getValidIntegerInput(input, 0, 4);

            switch (menuOption) {

            }

        } while (menuOption != 0);
    }

    public void manageSupportTicket() {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    0 - Exit to Logged In Menu
                    1 - View all active support tickets
                    2 - Edit Support-Ticket
                    3 - Edit User
                    4 - Remove User""");

            menuOption = getValidIntegerInput(input, 0, 4);

            switch (menuOption) {

            }


        } while (menuOption != 0);
    }
}
