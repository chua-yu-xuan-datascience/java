import java.util.Scanner;

public class PowerRaised {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // first input is number of loops
        int N = scanner.nextInt();
        
        // Sum of all results
        long X = 0;
        
        // Process each line
        for (int i = 1; i <= N; i++) {
            // read the base and exponent as string
            String temp = scanner.next();
            
            // exponent is the last digit and base is whatever is entire length-1
            int base = Integer.parseInt(temp.substring(0, temp.length() - 1)); 
            char lastChar = temp.charAt(temp.length() - 1);
            int exponent = lastChar - '0';
            
            // the power operation
            long result = (long) Math.pow(base, exponent);
            
            // Add the result to X
            X += result;
        }
        
        // Output the final sum
        System.out.println(X);
        
        scanner.close();
    }
}
