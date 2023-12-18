package Utils;// magnus nording, magnus.nording@iths.se
import java.util.Scanner;

public class UserInput {
    public static Scanner input = new Scanner(System.in);

    public static String readString() {
        String stringValue;
        do {
            stringValue = input.nextLine().trim();
            if (stringValue.isBlank()) {
                System.out.print("Ingen inmatning gjord, försök igen.\n > ");
            }
        } while (stringValue.isBlank());
        return stringValue;
    }

    public static int readInt() {
        int intValue;
        while (true) {
            try {
                String inputLine = input.nextLine();
                if (inputLine.isEmpty()) {
                    System.out.println("Ingen inmatning gjord, försök igen.");
                } else {
                    intValue = Integer.parseInt(inputLine);
                    if (intValue < 0) {
                        System.out.println("Värdet måste vara positivt, försök igen.");
                    } else {
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Felaktig inmatning, försök igen.");
            }
        }
        return intValue;
    }

    public static double readDouble() {
        double doubleValue;
        while (true) {
            String userInput = input.nextLine();
            userInput = userInput.replace(",", ".");
            try {
                doubleValue = Double.parseDouble(userInput);
                if (doubleValue < 0) {
                    System.out.println("Värdet måste vara positivt, försök igen.");
                } else {
                    return doubleValue;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Felaktig inmatning, försök igen.");
            }
        }
    }

    public static boolean readJaNej() {
        while (true) {
            System.out.print("Ange 'j' för ja eller 'n' för nej: ");
            String input = UserInput.readString();
            if (input.equalsIgnoreCase("j")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Ogiltigt val. Ange antingen 'j' eller 'n'.");
            }
        }
    }

    public static boolean readYesNo() {
        while (true) {
            System.out.print("Enter 'y' for yes or 'n' for no: ");
            String input = UserInput.readString();
            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Invalid choice. Enter either 'y' or 'n'.");
            }
        }
    }

    public static String capitalize(String originalString) {
        if (originalString.isEmpty()) {
            return originalString;
        }
        return originalString.substring(0, 1).toUpperCase() +
                originalString.substring(1);
    }

    public static void println(String line) {
        System.out.println(line);
    }

    public static void print(String line) {
        System.out.print(line);
    }


}
