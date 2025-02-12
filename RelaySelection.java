/*
  * create 3 arr,  1st one is to fix the runner 1, which we will remove from the 2nd arr. 
  * We will then keep permutating and swapping 3 runners available in 2nd arr and calculate the timing. 
  * if the timing is less than the lowest timing, replace it. 
  * Then when we run through all the possible 3 runner combinations with the 1st person as runner 1, 
  * we will go to 2nd person in arr1 as runner 1 and remove him from 2nd arr, and then repeat the process to calculate their total timings and finding the shortest time. 
  * Then finally the 3rd arr is to store the finalised team's names, the last 3 runners can be in any order but runner 1 must be the first name
  */

import java.util.*;

public class RelaySelection {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Read the number of runners for selection
        int n = scanner.nextInt();
        // ignore rest of the line and move on
        scanner.nextLine(); 
        
        // Create 1st arr --> list to store all runners info
        List<Runner> runners = new ArrayList<>();
        
        // Read each runner's data and add to 1st arr
        for (int i = 1; i <= n; i++) {
            String runner_name = scanner.next();
            double a = scanner.nextDouble();
            double b = scanner.nextDouble();
            scanner.nextLine();
            runners.add(new Runner(runner_name, a, b));
        }
        
        // Initialise fastest time and create 3rd arr to store the fastest team's names
        double fastestTime = Double.MAX_VALUE;
        List<String> fastestTeam = new ArrayList<>();
        
        // Iterate over each runner in 1st arr and fix them
        for (int i = 0; i < n; i++) {
            Runner firstRunner = runners.get(i);
            
            // Create 2nd arr that excludes the first runner
            List<Runner> remainingRunners = new ArrayList<>(runners);
            remainingRunners.remove(i);
            
            // Permutate 3 runners and calculate the team's time
            for (List<Runner> perm : getPermutations(remainingRunners)) {
                // Permutate n-1 runners but only select first three
                double time = firstRunner.a + perm.get(0).b + perm.get(1).b + perm.get(2).b;
                
                // if time is shorter, change fastest time and update the team's names
                if (time < fastestTime) {
                    fastestTime = time;
                    fastestTeam.clear();
                    fastestTeam.add(firstRunner.name); // Add first runner first
                    for (Runner r : perm) {
                        fastestTeam.add(r.name); // Add the other runners in the permutation order
                    }
                
                }
            }
        }
        
        // Output time and the team names
        System.out.printf("%.2f\n", fastestTime);
        // Print only the four selected runners
        for (int i = 0; i < 4; i++) {
            System.out.println(fastestTeam.get(i));
        }
        
        scanner.close();
    }

    // Helper method to get permutations of the list
    public static <T> List<List<T>> getPermutations(List<T> list) {
        List<List<T>> permutations = new ArrayList<>();
        permutationsHelper(list, 0, permutations);
        return permutations;
    }

    private static <T> void permutationsHelper(List<T> list, int start, List<List<T>> permutations) {
        if (start == list.size()) {
            permutations.add(new ArrayList<>(list));
            return;
        }
        for (int i = start; i < list.size(); i++) {
            Collections.swap(list, start, i);
            permutationsHelper(list, start + 1, permutations);
            Collections.swap(list, start, i); // backtrack
        }
    }

    // specify Runner class to hold information about a runner
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

/*You are the coach of the national athletics team and need to select which sprinters should represent your country in the 4 x100 m relay in the upcoming championships. 
As the name of the event implies, such a sprint relay consist of 
  4legs,  
 100 meters each. One would think that the best team would simply consist of the  
 4 fastest  
 100m runners in the nation, but there is an important detail to take into account: flying start. In the  
2nd,  
3rd and  
4th leg, the runner is already running when the baton is handed over. This means that some runners – those that have a slow acceleration phase – can perform relatively better in a relay if they are on the  
2nd,  
3rd or  
4th leg. 

You have a pool of runners to choose from. Given how fast each runner in the pool is, decide which four runners should represent your national team and which leg they should run. You are given two times for each runner – the time the runner would run the 
1st leg, and the time the runner would run any of the other legs. A runner in a team can only run one leg. 

Input
The first line of input contains an integer n 
, the number of runners to choose from (4 <= n <=500 
). Then follow n 
 lines describing the runners. The 
i’th of these lines contains the name of the  
i’th runner, the ai time  
 for the runner to run the 
1st leg, and the bi time  
 for the runner to run any of the other legs (8 <= bi <= ai <= 20 
). The names consist of between 2 
 and 20 
 (inclusive) uppercase letters ‘A’-‘Z’, and no two runners have the same name. The times are given in seconds with exactly two digits after the decimal point.

Output
First, output a line containing the time of the best team, accurate to an absolute or relative error of at most 10^-9 
. Then output four lines containing the names of the runners in that team. The first of these lines should contain the runner you have picked for the 
1st leg, the second line the runner you have picked for the  
2nd leg, and so on. Any solution that results in the fastest team is acceptable.

sample input:
6
ASHMEADE 9.90 8.85
BLAKE 9.69 8.72
BOLT 9.58 8.43
CARTER 9.78 8.93
FRATER 9.88 8.92
POWELL 9.72 8.61

sample output:
35.54
CARTER
BOLT
POWELL
BLAKE
 */

 