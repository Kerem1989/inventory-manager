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



}