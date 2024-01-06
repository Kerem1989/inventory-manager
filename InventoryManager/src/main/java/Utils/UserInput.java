package Utils;// magnus nording, magnus.nording@iths.se
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {
    private static final UserInput instance = new UserInput();
    private static final Scanner input;

    static {
        input = new Scanner(System.in);
    }

    private UserInput() {}

    public static UserInput getInstance() {
        return instance;
    }

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

    public static int readInt(int min, int max) {
    int intValue;
    while (true) {
        try {
            String inputLine = input.nextLine();
            if (inputLine.isEmpty()) {
                System.out.println("Ingen inmatning gjord, försök igen.");
            } else {
                intValue = Integer.parseInt(inputLine);
                if (intValue < min || intValue > max) {
                    System.out.printf("Värdet måste vara mellan %d och %d, försök igen.\n", min, max);
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

    public static long readLong() {
        long longValue;
        while (true) {
            try {
                if (input.hasNextLong()) {
                    longValue = input.nextLong();
                    input.nextLine();
                    break;
                } else {
                    System.out.println("Felaktig inmatning, försök igen.");
                    input.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Felaktig inmatning, försök igen.");
                input.nextLine();
            }
        }
        return longValue;
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

    public void closeScanner() {
        if (input != null) {
            input.close();
        }
    }
}
