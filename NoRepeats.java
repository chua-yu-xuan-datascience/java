import java.util.Scanner;

public class NoRepeats {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the name
        String originalname = scanner.nextLine();

        StringBuilder shortname = new StringBuilder();

        // store previous character
        char prevChar = '\0';

        // Iterate over length of input name
        for (int i = 0; i < originalname.length(); i++) {
            char currChar = originalname.charAt(i);

            // Check currChar is not same as prevChar
            if (currChar != prevChar) {
                shortname.append(currChar);
            }

            // move prevChar up a char
            prevChar = currChar;
        }

        System.out.println(shortname.toString());

        scanner.close();
    }
}
