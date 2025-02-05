import java.util.Scanner;

public class Enlarge {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int R = scanner.nextInt();
        int C = scanner.nextInt();
        int Zr = scanner.nextInt();
        int Zc = scanner.nextInt();
        scanner.nextLine();  // move on to next input line

        // read original matrix by row
        String[] orig_matrix = new String[R];
        for (int i = 0; i < R; i++) {
            orig_matrix[i] = scanner.nextLine();
        }

        // Start from row 0 to R-1, enlarge columns first
        for (int i = 0; i < R; i++) {
            StringBuilder enlarge_matrix = new StringBuilder(); // Create the item to store new characters
            for (int j = 0; j < C; j++) {
                char character = orig_matrix[i].charAt(j); // Going row by row, col by col to identify each char
                // repeat that character Zc times to create enlarged columns
                for (int a = 1; a <= Zc; a++) {
                    enlarge_matrix.append(character);
                }
            }

            // When finish enlarged col, repeat that particular row Zr times to create enlarged rows
            for (int b = 1; b <= Zr; b++) {
                System.out.println(enlarge_matrix.toString());
            }
        }

        scanner.close();
    }
}
