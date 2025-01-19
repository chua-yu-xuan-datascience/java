import java.util.Scanner;

public class Shortened {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // input
        String input = scanner.nextLine();
        
        // function
        String result = shorten(input);
        
        // output
        System.out.println(result);
        
        scanner.close();
    }
    
    // shorten function
    public static String shorten(String input) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            
            // only want uppercase in result
            if (Character.isUpperCase(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }
}



