import java.util.Scanner;

public class OnesFactorial {
    public static void main(String[] args) {
        // last digits
        int[] onesdigit = {1, 2, 6, 4, 0, 0, 0, 0, 0, 0};

        Scanner scanner = new Scanner(System.in);

        // first input is number of iterations
        //int T = scanner.nextInt();

        // iterate through the next inputs
        for (int t = 1; t <= 3; t++) {
            int N = scanner.nextInt();
            System.out.println(onesdigit[N-1]);
        }

        scanner.close();
    }
}

