package se.what.inventorymanager;

public class Output {
    public void introText() {

    }

    public void login() {

    }

    public void menuAdmin() {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    1 - Manage Users
                    2 - Manage Equipment
                    3 - Manage Support tickets""");
                switch (menuOption){
                    case 1 -> manageUsersMenu();
                    case 2 -> manageEquipment();
                    case 3 -> manageSupportTicket();
                }


        } while(menuOption != 0);
    }



    public void menuUser() {
        int menuOption = 0;
        do {


            System.out.println("""
                    Choose option below:
                    1 - Search available equipment in stock
                    2 - Display your equipment
                    3 - Support ticket
                    """);

            switch (menuOption){
                case 1 -> System.out.println("display!!!!");
            }
        }while(menuOption!=0);
    }

    private void manageUsersMenu() {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    1 - Display all users
                    2 - Add User
                    3 - Edit User
                    4 - Remove User""");
            switch (menuOption){

            }


        } while(menuOption != 0);

    }

    private void manageEquipment() {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    1 - Display all users
                    2 - Add User
                    3 - Edit User
                    4 - Remove User""");
            switch (menuOption){

            }


        } while(menuOption != 0);
    }

    public void manageSupportTicket() {
        int menuOption = 0;

        do {
            System.out.println("""
                    Choose option below:
                    1 - View all active support tickets
                    2 - Edit Support-Ticket
                    3 - Edit User
                    4 - Remove User""");
            switch (menuOption){

            }


        } while(menuOption != 0);
    }
}
