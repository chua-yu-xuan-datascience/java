import java.util.Scanner;

public class Restaurant {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // N is number of restaurants/ iterations
        int N = scanner.nextInt();
        // ignore rest of the line and move on
        scanner.nextLine(); 
        
        // outer loop for each restaurant
        for (int i = 1; i <= N; i++) {
            // read the number of items in restaurant
            int M = scanner.nextInt();
            scanner.nextLine();
            
            String name = scanner.nextLine();  // The name of the restaurant
            boolean PeaSouppresent = false;
            boolean Pancakespresent = false;
            
            // inner loop with M items
            for (int j = 1; j <= M; j++) {
                String item = scanner.nextLine(); // item name
                // check if they == to "pea soup" and "pancakes"
                if (item.equals("pea soup")) {
                    PeaSouppresent = true;
                }
                if (item.equals("pancakes")) {
                    Pancakespresent = true;
                }
            }
            // If both "pea soup" and "pancakes" are found, output restaurant name and leave program
            if (PeaSouppresent && Pancakespresent) {
                System.out.println(name);
                return;
            }
        }
        
        // If end of outer loop, no restaurant has both true, return this message
        System.out.println("Anywhere is fine I guess"); 
        
        scanner.close();
    }
}