/*
We first read the total no. of researchers (n) and the time of inactivity before being locked (m)
then in each researcher, we save information into researcher class, with arrivalTime (a) and stayTime (s)
Need to compare and sort the reseachers by their arrivalTime
minheap is to track the time that we can use a workstation (arrival+stay of the researcher using it now)

1st state: used (peek > arrival) (station wont poll, we just add researcher), 
2nd state: unused and unlocked (peek <= arriv <= peek +m) (poll and add researcher and maxUnlockingsaved++), 
3rd state: unused and locked (peek+m < arriv) (poll all of them and add researcher)
*/

import java.util.*;

class Researcher {
    int arrivalTime;
    int stayTime;
    
    public Researcher(int a, int s) {
        arrivalTime = a;
        stayTime = s;
    }
}

public class UnlockDesk {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // total no. of researchers (n) and the time of inactivity before being locked (m)
        int n = sc.nextInt();
        int m = sc.nextInt();
        
        List<Researcher> researchers = new ArrayList<>();
        
        // Read the researchers' arrival time and stay time
        for (int i = 0; i < n; i++) {
            int a = sc.nextInt();
            int s = sc.nextInt();
            researchers.add(new Researcher(a, s));
        }
        
        // compare and sort the reseachers by their arrivalTime
        researchers.sort((r1, r2) -> Integer.compare(r1.arrivalTime, r2.arrivalTime));
        
        // Min-heap to track the time that we can use a workstation
        PriorityQueue<Integer> workStations = new PriorityQueue<>();
        
        // Variable to count unlocks Penelope saves
        int maxUnlockingsaved = 0;
        
        // Process each researcher
        for (Researcher researcher : researchers) {
            int arrival = researcher.arrivalTime;
            int stay = researcher.stayTime;
            
            // 1st state: used (peek > arrival) (station wont poll, we just add researcher), 
            // 2nd state: unused and unlocked (peek <= arriv <= peek +m) (poll and add researcher and maxUnlockingsaved++), 
            // 3rd state: unused and locked (peek+m < arriv) (poll all of them and add researcher)

            // 3rd state
            while (!workStations.isEmpty() && workStations.peek() +m < arrival) {
                workStations.poll();  
            }
            
            // 2nd state
            if (!workStations.isEmpty() && (workStations.peek()<= arrival && arrival <= workStations.peek() + m)) {
                workStations.poll(); 
                maxUnlockingsaved++;  
            }
            
            // Add a workstation used by researcher
            workStations.add(arrival + stay); 
        }
        
        // Output the result
        System.out.println(maxUnlockingsaved);

        sc.close();
    }
}

