import java.util.Scanner;

public class SameorDifferent {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // N is number of cases/ iterations
        int N = scanner.nextInt();
        // ignore rest of the line and move on
        scanner.nextLine(); 
        
        // outer loop for each case
        for (int i = 1; i <= N; i++) {
            // read the two strings
            String string1 = scanner.nextLine();
            String string2 = scanner.nextLine();
            
            // process their differences
            StringBuilder result = new StringBuilder();
            for (int j = 0; j < string1.length(); j++) {
                if (string1.charAt(j) == string2.charAt(j)) {
                    result.append('.');
                } else {
                    result.append('*');
                }
            }

            // output the two strings
            System.out.println(string1);
            System.out.println(string2);

            // output the result
            System.out.println(result);
            
            // output a blank line to separate from the next case
            System.out.println();
        }
        
        scanner.close();
    }
}

