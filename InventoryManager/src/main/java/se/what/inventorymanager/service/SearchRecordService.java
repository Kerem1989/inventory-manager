package se.what.inventorymanager.service;
import java.util.List;
import java.util.Scanner;

import Utils.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.what.inventorymanager.domain.Equipment;
import se.what.inventorymanager.domain.SearchRecord;
import se.what.inventorymanager.domain.User;
import se.what.inventorymanager.repository.*;

@Service
public class SearchRecordService {

    public static Scanner input = new Scanner(System.in);

    @Autowired
    SearchRecordRepo searchRecordRepo;
    public SearchRecordService(SearchRecordRepo searchRecordRepo) {
        this.searchRecordRepo = searchRecordRepo;
    }

    public static void searchRecordMenu(SearchRecordRepo searchRecordRepo) {

        int userInput = 0;
        do {
            System.out.println("\nPlease choose an option by number\nSearch record:\n" +
                    "0 - Return to main menu\n" +
                    "1 - Display all search words\n" +
                    "2 - Display all unique search words\n" +
                    "3 - Display number of searches by type of search\n");
            try {
                
                userInput = InputOutput.getValidIntegerInput(input,0,3);
                switch (userInput) {

                    case 1 -> printAllSearches(searchRecordRepo);
                    case 2 -> searchByQuery(searchRecordRepo);
                    case 3 -> sumOfQueries(searchRecordRepo);
                    default -> System.out.println("Your choice could not be read, please try again.");
                }

            } catch (Exception e) {
                System.out.print("Your choice could not be read, please try again.");
            }
            

        } while (userInput!=0);
        System.out.println("Returning back to main menu.\n\n");



    }

    //metod för att lägga till nya sökningar i databasen :)
    public static void printAllSearches(SearchRecordRepo searchRecordRepo){
        System.out.println("Search record:");
        var foundRecords = searchRecordRepo.findAll();
        for (SearchRecord foundRecord:foundRecords) {
            System.out.println(foundRecord);
        }
    }

    public static void searchByQuery(SearchRecordRepo searchRecordRepo){
        System.out.print("Search to display the unique searches for this query.\nWhat query would you like to display? > ");
        String searchByQuery = input.nextLine();
        boolean found = false;
        System.out.println("Search record: ");
        var foundRecords = searchRecordRepo.findAll();
        for (SearchRecord foundRecord : foundRecords) {
            if (foundRecord.getQuery().contains(searchByQuery)){
                System.out.println("Found:");
                System.out.println(foundRecord);
                found = true;
            }
        }
        if (!found) {
            System.out.println("None found.");
        }
    }

    public static void sumOfQueries(SearchRecordRepo searchRecordRepo) {
        System.out.println("To see how many times a certain query occurs in users search history.");
        System.out.print("What query would you like to display? > ");
        String searchByQuery = input.nextLine();
        int count = 0;
        System.out.println("Number of finds: ");
        var foundRecords = searchRecordRepo.findAll();
        for (SearchRecord foundRecord : foundRecords) {
            if (foundRecord.getQuery().contains(searchByQuery)){
                count ++;
            }
        }
        if (count > 0) {
            System.out.println("'" + searchByQuery + "'" + " has been searched for " + count + " times");
        } else {
            System.out.println("None found");
        }
    }


    public static void equipmentSearchQueryMenu(UserRepo userRepo, UnassignedEquipmentRepo unassignedEquipmentRepo, User foundUser, EquipmentRepo equipmentRepo, EquipmentSupportRepo equipmentSupportRepo, SearchRecordRepo searchRecordRepo) {

        int menuOption;
        do {
            System.out.println("""
                    Choose option below:
                    0 - Back to main menu
                    1 - free text search equipment
                    2 - Display all available equipment
                    """);

            menuOption = InputOutput.getValidIntegerInput(input, 0, 2);

            switch (menuOption) {
                case 1 -> {
                    SearchRecord searchRecord = new SearchRecord();
                    System.out.println("Type search: ");
                    String query = InputOutput.getValidStringInput(input);
                    searchRecord.setQuery(query);
                    searchRecordRepo.save(searchRecord);

                    Equipment equipment = new Equipment();
                    boolean found = false;
                    List<Equipment> foundEquipment = equipmentRepo.findAll();

                    for (Equipment foundEq : foundEquipment) {

                            if (foundEq.getName().toLowerCase().contains(query.toLowerCase())){
                                System.out.println("Found:");
                                System.out.println(foundEq);
                                found = true;
                            }

                    }
                    if (!found) {
                        System.out.println("\nNone found.\n");
                    }else {
                        System.out.println("\nplease contact your manager to request loan with Id of Equipment\n");
                    }

                }
                case 2 -> System.out.println("The following equipment is not assigned to anybody, please contact your manager to request loan with Id of Equipment\n"+
                        unassignedEquipmentRepo.findAll());
            }
        } while (menuOption != 0);





    }
}