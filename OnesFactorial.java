import java.util.Scanner;

public class OnesFactorial {
    public static void main(String[] args) {
        // Hardcoded last digits of factorials from 0! to 10!
        int[] onesdigit = {1, 2, 6, 4, 0, 0, 0, 0, 0, 0};

        // Create a scanner to read input
        Scanner scanner = new Scanner(System.in);

        // Read the number of test cases
        int T = scanner.nextInt();

        // Process each test case
        for (int t = 1; t < T; t++) {
            int N = scanner.nextInt();
            System.out.println(onesdigit[N-1]);
        }

        // Close the scanner
        scanner.close();
    }
}

