import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }
    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public String getWord() {
        return nextToken();
    }

    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}

class LinkedListNode {
    String data;
    LinkedListNode next;

    // Constructor to initialize Linkedlist node with a string as its data
    public LinkedListNode(String data) {
        this.data = data;
        this.next = null;
    }
}

public class FormTheSentence {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        
        // N for number of strings
        int N = io.getInt();
        
        LinkedListNode[] nodes = new LinkedListNode[N];
        LinkedListNode[] lastNode = new LinkedListNode[N];
        
        // Read all strings and make a node contain each string
        for (int i = 0; i < N; i++) {
            String word = io.getWord();
            nodes[i] = new LinkedListNode(word);
            lastNode[i] = nodes[i];
        }
        
        // Merge strings
        for (int i = 0; i < N - 1; i++) {
            int a = io.getInt() - 1; // first string
            int b = io.getInt() - 1; // second string
            
            // Link lastnode of linkedlist a to start of b
            lastNode[a].next = nodes[b];
            
            // Update the lastNode to be the lastNode of linkedlist b
            lastNode[a] = lastNode[b];
            
            // b has no data now
            nodes[b] = null;
        }

        // Find the first non-null node
        LinkedListNode finalNode = null;
        for (int i = 0; i < N; i++) {
            if (nodes[i] != null) {
                finalNode = nodes[i];
                break;
            }
        }
        
        // Build the final result string using StringBuilder
        StringBuilder result = new StringBuilder();
        
        while (finalNode != null) {
            result.append(finalNode.data);
            finalNode = finalNode.next;
        }

        io.println(result.toString());
        
        io.close();
    }
}




/*
public class FormTheSentence {
    public static void main(String[] args) {
        
        Kattio io = new Kattio(System.in, System.out);
        
        // N for number of strings
        int N = io.getInt();
        
        // Use stringbuilder than arrays, so that dont have to modify without creating new objects
        StringBuilder[] strings = new StringBuilder[N];

        // add strings to stringbuilder
        for (int i = 0; i < N; i++) {
            strings[i] = new StringBuilder(io.getWord());  
        }
        
        // join the strings together
        for (int i = 0; i < N - 1; i++) {
            int a = io.getInt() - 1; // for indexing purposes
            int b = io.getInt() - 1; 
            
            // Append string at pos b to string at pos a 
            strings[a].append(strings[b]);  

            // Empty at pos b
            strings[b] = new StringBuilder();  
            }
        
        // output the one slot that isnt empty (has our final sentence)
        for (int i = 0; i < N; i++) {
            if (!strings[i].isEmpty()) {
                io.println(strings[i]);
                break;
            }
        }
        
        io.close();
    }
}
*/


/*You are given a collection of N non-empty strings, denoted by S1, S2,... Sn. Then you are given 
N-1 operations which you execute in the order they are given. The ith
operation is has the following format: ‘ab’ (1-based indexing, without the quotes), 
which means that you have to make the following changes:
- Sa = Sa + Sb, i.e. concatenate ath string and bth string and store the result in ath string,
- Sb = "", i.e. make the bth string empty, after doing the previous step.

You are ensured that after the i-th operation, there will be no future operation that will be accessing 
Sb. Given these operations to join strings, print the last string that will remain at the end of this process. 

Input
The first line contains an integer N (1 <= N <= 10 ^5) denoting the number of strings given. Each of the next N
lines contains a string denoting the Si. All the characters in the string Si are lowercase alphabets from ‘a’ to ‘z’. 
The total number of characters over all the strings is at most 10^6, i.e sumn i=1 |Si| <= 10^6, where |Si| 
denotes the length of the ith string. After these N strings, each of the next N-1 lines contain two integers 
a and b, such that a =/= b and 1 <= a,b <= N  denoting the i=th operation.

Output
Print the last string which remains at the end of the N-1 operations.
*/