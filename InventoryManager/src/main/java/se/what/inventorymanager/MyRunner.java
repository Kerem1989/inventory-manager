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
        user.setName(InputOutput.getUserData("Please enter the users full name: "));
        user.setDepartment(InputOutput.getUserData("Please enter the users department: "));
        user.setEmail(InputOutput.getUserData("Please enter the users email: "));
        user.setTelephone(InputOutput.getUserData("Please enter the users telephone number: "));
        user.setUsername(InputOutput.getUserData("Please choose a username for the user: "));
        user.setPassword(InputOutput.getUserData("Please choose a password for the user: "));

        InputOutput.getUserData("Please enter user-role\n1 - Admin\n2 - SuperUser\n3 - Regular user");

        int userRole = InputOutput.getValidIntegerInput(InputOutput.input,1,3);

        switch (userRole){
            case 1 -> user.setRole(RoleType.admin);
            case 2 -> user.setRole(RoleType.superuser);
            case 3 -> user.setRole(RoleType.user);
        }
        userRepo.save(user);
        InputOutput.getUserData("You have added " + user.getName() + " as a user with " + user.getRole() + "-Access");
    }



}