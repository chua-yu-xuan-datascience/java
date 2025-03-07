/*
Read the input which consists of n lines (number of students)
each student chooses a combination of five distinct courses.
HashMap to store the frequency of each combination of courses - key in the map will be the combi (sorted array with courses as integer) and the value will be frequency of students selecting that combination
Sort the course numbers to ensure that combinations are treated the same regardless of the order.
populate the HashMap - HashMap<List<Integer>, Integer> to store the frequency of each combination. if alr exist in map, freq +1, else initialise as 1
find the most popular combination(s) (by their frequencies) and the count of students with most popular combinations
Iterate through the map to find the highest frequency, if another combi has same freq, count +=1,. if found higher freq, count = freq again and highest freq  = freq
*/

import java.util.*;

public class FroshCourses {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // input number of students
        int n = scanner.nextInt();
        scanner.nextLine();

        // create HashMap to store the student course combinations and their frequencies
        Map<List<Integer>, Integer> froshMap = new HashMap<>();

        // read the course combinations and put into hashmap
        for (int i = 0; i < n; i++) {
            String[] courseString = scanner.nextLine().split(" ");
            List<Integer> courses = new ArrayList<>(5);

            // Convert each course to integer first
            for (String course : courseString) {
                courses.add(Integer.parseInt(course));
            }

            // Sort in ascending order to ensure combinations are treated the same irrespective of order
            Collections.sort(courses);

            // Use the sorted list as the key and update its frequency in the map
            froshMap.put(courses, froshMap.getOrDefault(courses, 0) + 1);
        }

        // Find the highest frequency found in the combis and count the students taking these combi(s)
        int highestFrequency = 0;
        int totalCountFrosh = 0;

        for (int freq : froshMap.values()) {
            if (freq == highestFrequency) {
                totalCountFrosh += freq; // add number of students also taking this most popular combi

            } else if (freq > highestFrequency) {
                highestFrequency = freq;
                totalCountFrosh = freq;  // replace all with new highest freq
            }
        }

        // output the total count of frosh who selected the most popular combi(s)
        System.out.println(totalCountFrosh);
        scanner.close();
    }
}
