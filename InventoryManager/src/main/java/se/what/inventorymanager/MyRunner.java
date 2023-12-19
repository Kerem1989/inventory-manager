package se.what.inventorymanager;

import Utils.InputOutput;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        InputOutput.introText();
        InputOutput.login();
    }
}