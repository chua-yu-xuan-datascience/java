/*
 * create the arr with key as alphabets and values as the numbers
 * first input/integer is number of cases/ iterations
 * for each line, read char by char
 * in a stringbuilder, put in the seq of num
 * if the last num is same as next num, leave a space between them, actually can tell they are same number can already
 * if there is a blank, put "0" 
 */
import java.util.Scanner;

public class PhoneMessage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // the mapping from char to number
        String[] arr = new String[256];
        arr['a'] = "2";
        arr['b'] = "22";
        arr['c'] = "222"; 
        arr['d'] = "3"; 
        arr['e'] = "33";
        arr['f'] = "333";
        arr['g'] = "4";
        arr['h'] = "44"; 
        arr['i'] = "444";
        arr['j'] = "5";
        arr['k'] = "55";
        arr['l'] = "555";
        arr['m'] = "6";
        arr['n'] = "66";
        arr['o'] = "666";
        arr['p'] = "7";
        arr['q'] = "77";
        arr['r'] = "777";
        arr['s'] = "7777";
        arr['t'] = "8";
        arr['u'] = "88";
        arr['v'] = "888";
        arr['w'] = "9";
        arr['x'] = "99";
        arr['y'] = "999";
        arr['z'] = "9999";
        arr[' '] = "0";  

        // Number of test cases/ iterations
        int N = scanner.nextInt();  
        scanner.nextLine();
        
        // iterate each test case/ msg
        for (int i = 1; i <= N; i++) {
            String inputmsg = scanner.nextLine(); // read the whole message
            StringBuilder resultformsg = new StringBuilder(); // to store the numbers
            char prevchar = '\0'; // to store the previous character because need to compare later 
            
            // iterate character by character in each msg 
            for (char c : inputmsg.toCharArray()) {
                String charseq = arr[c];
                
                // Leave a space if the output from prev char and output from current char have the same number
                if (prevchar != '\0' && arr[prevchar].charAt(0) == arr[c].charAt(0)) {
                    resultformsg.append(" "); 
                }
                
                resultformsg.append(charseq); 
                prevchar = c;
            }

            // output the whole message's result 
            System.out.println("Case #" + i + ": " + resultformsg.toString());
        }
        scanner.close(); 
    }
}
