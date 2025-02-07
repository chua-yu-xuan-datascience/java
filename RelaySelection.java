import java.util.Scanner;

public class RelayAssemble {
    // loop from first runner's first time
    // create many teams by taking other 3 runner's second time
    // take run time as key, and team names as value
    // sort dict, return the 1st key and its value
}

import java.util.*;

public class RelaySelection {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read number of runners
        int n = sc.nextInt();
        sc.nextLine();  // Consume newline
        
        List<Runner> runners = new ArrayList<>();
        
        // Read the runners' data
        for (int i = 0; i < n; i++) {
            String name = sc.next();
            double a = sc.nextDouble();  // Time for the first leg (standing start)
            double b = sc.nextDouble();  // Time for the other legs (flying start)
            sc.nextLine();  // Consume newline
            runners.add(new Runner(name, a, b));
        }
        
        double bestTime = Double.MAX_VALUE;
        List<Runner> bestTeam = new ArrayList<>();
        
        // Try all combinations of 4 runners from the available list
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        // Select 4 runners: i, j, k, l
                        List<Runner> team = Arrays.asList(runners.get(i), runners.get(j), runners.get(k), runners.get(l));
                        
                        // Generate all permutations of the selected 4 runners
                        List<List<Runner>> permutations = permute(team);
                        
                        // Evaluate each permutation and calculate the total time
                        for (List<Runner> perm : permutations) {
                            double totalTime = perm.get(0).a + perm.get(1).b + perm.get(2).b + perm.get(3).b;
                            
                            // If this is the best time so far, update the best team
                            if (totalTime < bestTime) {
                                bestTime = totalTime;
                                bestTeam = perm;
                            }
                        }
                    }
                }
            }
        }
        
        // Output the best team and its total time
        System.out.printf("%.9f\n", bestTime);
        for (Runner runner : bestTeam) {
            System.out.println(runner.name);
        }
        
        sc.close();
    }
    
    // Helper function to generate all permutations of a list
    private static <T> List<List<T>> permute(List<T> list) {
        List<List<T>> result = new ArrayList<>();
        if (list.size() == 1) {
            result.add(new ArrayList<>(list));
            return result;
        }
        
        for (int i = 0; i < list.size(); i++) {
            T current = list.get(i);
            List<T> remaining = new ArrayList<>(list);
            remaining.remove(i);
            List<List<T>> permutations = permute(remaining);
            for (List<T> perm : permutations) {
                perm.add(0, current);
                result.add(perm);
            }
        }
        
        return result;
    }
    
    // Runner class to store runner data (name, first leg time, and other legs time)
    static class Runner {
        String name;
        double a;  // Time for the first leg
        double b;  // Time for the other legs
        
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