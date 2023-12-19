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

    @Override
    public void run(String... args) throws Exception {
        InputOutput.introText();
        InputOutput.login();

    }
}