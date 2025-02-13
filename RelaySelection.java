/*
  * create 3 arr,  1st one is to fix the runner 1, which we will remove from the 2nd arr. 
  * compare the b timings to rank runners by their b timing. 
  * take the b time of the first 3 runners + a time of first runner as total time. 
  * if time is shorter than current besttime, update time and the team's names
*/
import java.util.*;

public class RelaySelection {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the number of runners
        int n = scanner.nextInt();
        scanner.nextLine(); // ignore the rest of the line

        // Create a list to store all runners' info
        List<Runner> runners = new ArrayList<>();

        // Read each runner's data and add to the list
        for (int i = 0; i < n; i++) {
            String runnerName = scanner.next();
            double a = scanner.nextDouble();
            double b = scanner.nextDouble();
            scanner.nextLine();
            runners.add(new Runner(runnerName, a, b));
        }

        // Initialize fastest time and create a list to store the fastest team's names
        double fastestTime = Double.MAX_VALUE;
        List<String> fastestTeam = new ArrayList<>();

        // Iterate over each runner as the first runner
        for (int i = 0; i < n; i++) {
            Runner firstRunner = runners.get(i);

            // Create a list of remaining runners excluding the first runner
            List<Runner> remainingRunners = new ArrayList<>(runners);
            remainingRunners.remove(i);

            // Sort remaining runners by their b
            remainingRunners.sort(Comparator.comparingDouble(r -> r.b));

            // take the first three fastest remaining runners
            if (remainingRunners.size() >= 3) {
                Runner secondRunner = remainingRunners.get(0);
                Runner thirdRunner = remainingRunners.get(1);
                Runner fourthRunner = remainingRunners.get(2);

                // Calculate the time for this team
                double time = firstRunner.a + secondRunner.b + thirdRunner.b + fourthRunner.b;

                // If this time is the fastest, update the fastest time and team names
                if (time < fastestTime) {
                    fastestTime = time;
                    fastestTeam.clear();
                    fastestTeam.add(firstRunner.name);
                    fastestTeam.add(secondRunner.name);
                    fastestTeam.add(thirdRunner.name);
                    fastestTeam.add(fourthRunner.name);
                }
            }
        }

        // Output the fastest time and the team names
        System.out.printf("%.2f\n", fastestTime);
        for (String name : fastestTeam) {
            System.out.println(name);
        }

        scanner.close();
    }

    // Define the Runner class to store information about each runner
    static class Runner {
        String name;
        double a, b;

        Runner(String name, double a, double b) {
            this.name = name;
            this.a = a;
            this.b = b;
        }
    }
}