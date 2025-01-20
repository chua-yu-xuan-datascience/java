import java.util.Scanner;

public class NumbersRelation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // first input is number of iterations
        int N = scanner.nextInt();

        // iterate through the next lines of inputs
        for (int i = 1; i <= N; i++) {
            // int a, b, and c in line
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = scanner.nextInt();

            // run through all the operations
            if (a + b == c || a - b == c || b - a == c || a * b == c || 
                (a / b == c && a % b == 0 && b != 0) || 
                (b / a == c && b % a == 0 && a != 0)) {
                System.out.println("Possible");
            } else {
                System.out.println("Impossible");
            }
        }

        scanner.close();
    }
}

