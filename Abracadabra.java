import java.util.Scanner;

public class Abracadabra {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //int N = scanner.nextInt();
        
        // Loop through 1 to N and print the output
        for (int i = 1; i <= 10; i++) {
            System.out.println(i + " Abracadabra");
        }
        
        scanner.close();
    }
}
