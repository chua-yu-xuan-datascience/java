/* input first integer for number of words/iterations
 * Sort the word's middle alphabet by using compareto which will sort the smaller alphabet first
 * how to get the middle alphabets? -> odd length: the index is len/2 while for even length: the indexes are len/2 -1 and len/2
 * even after the middle alphabets, both single and double, are the same, sort by the order that the words were inputted
 * so the list we should be sorting must also contain the position index that the words are inputted in
 * and need to compare twice (first by middlechar then by position index): stable sort needed
 * then we output the word portion of the class in the list
 */
import java.util.*;

public class MiddleAlphabet {
    
    // Method to get the middle char
    public static String getMiddleChar(String word) {
        int len = word.length();
        if (len % 2 == 1) {
            // odd length: the index is len/2
            return String.valueOf(word.charAt(len/2));
        } else {
            // even length: the indexes are len/2 -1 and len/2
            return word.substring(len/2 - 1, len/2 + 1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Number of words
        int N = scanner.nextInt();
        scanner.nextLine(); 
        
        // Create a list WordandIndex to store the words and the index they were inputted in
        // Need to create class to later retrieve the word and index components easily
        List<WandI> WordandIndex = new ArrayList<>();
        
        // add word and index they were inputted in
        for (int i = 0; i < N; i++) {
            String word = scanner.nextLine();
            WordandIndex.add(new WandI(word, i));
        }
        
        // Sort the list
        Collections.sort(WordandIndex, (wi1, wi2) -> {
            // Compare using middlechar first
            String middle1 = getMiddleChar(wi1.word);
            String middle2 = getMiddleChar(wi2.word);
            int result = middle1.compareTo(middle2);
            
            // If the middle characters are the same, sort by their index
            if (result == 0) {
                return Integer.compare(wi1.index, wi2.index);
            }
            return result;
        });
        
        // Print the word portion
        for (WandI wi : WordandIndex) {
            System.out.println(wi.word);
        }
        
        scanner.close();
    }

    // WandI class to store word and its original index
    static class WandI {
        String word;
        int index;

        WandI(String word, int index) {
            this.word = word;
            this.index = index;
        }
    }
}
