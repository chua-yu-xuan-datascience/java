import java.util.Scanner;

public class Abracadabra {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        //input
        int N = scanner.nextInt();
        
        // Iterate from 1 to N
        for (int i = 1; i <= N; i++) {
            System.out.println(i + " Abracadabra");
        }
        
        scanner.close();
    }
}
