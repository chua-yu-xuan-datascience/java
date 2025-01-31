import java.util.Scanner;

public class Fizbo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       
        int X = scanner.nextInt();
        int Y = scanner.nextInt();
        int N = scanner.nextInt();
        
        // Iterate through numbers 1 to N
        for (int i = 1; i <= N; i++) {
            // Check if divisible by both X and Y first
            if (i % X == 0 && i % Y == 0) {
                System.out.println("FizzBuzz");
            }
            
            else if (i % X == 0) {
                System.out.println("Fizz");
            }
            
            else if (i % Y == 0) {
                System.out.println("Buzz");
            }
            // Otherwise, print the number
            else {
                System.out.println(i);
            }
        }
        
        scanner.close();
    }
}