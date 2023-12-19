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

    Scanner input = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        //InputOutput.introText();
        //InputOutput.login();
        addNewUser(userRepo, input);

    }

    public static void addNewUser(UserRepo userRepo, Scanner input) {
        User user = new User();
        System.out.print("Please enter the users full name: ");
        String adminInputName = InputOutput.getValidStringInput(input);
        user.setName(adminInputName);
        System.out.print("Please enter the users department: ");
        String adminInputDepartment = InputOutput.getValidStringInput(input);
        user.setDepartment(adminInputDepartment);
        System.out.print("Please enter the users email: ");
        String adminInputEmail= InputOutput.getValidStringInput(input);
        user.setEmail(adminInputEmail);
        System.out.print("Please enter the users telephone number: ");
        String adminInputTelephone = InputOutput.getValidStringInput(input);
        user.setTelephone(adminInputTelephone);
        System.out.print("Please choose a username for the user: ");
        String adminInputUsername = InputOutput.getValidStringInput(input);
        user.setUsername(adminInputUsername);
        System.out.print("Please choose a password for the user: ");
        String adminInputPassword = InputOutput.getValidStringInput(input);
        user.setPassword(adminInputPassword);
        System.out.print("Please enter a role for the user, must equal admin, superuser or user: ");
        String adminInputRole = InputOutput.getValidStringInput(input);
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

}