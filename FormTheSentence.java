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

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public long getLong() {
        return Long.parseLong(nextToken());
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
    String value;
    LinkedListNode next;

    // Constructor to initialize Linkedlist node with a string value (our word later)
    public LinkedListNode(String value) {
        this.value = value;
        this.next = null;
    }
}


public class FormTheSentence {
    public static void main(String[] args) {
        
        Kattio io = new Kattio(System.in, System.out);
        
        // N for number of strings
        int N = io.getInt(); 
        
        LinkedListNode[] nodes = new LinkedListNode[N];
        
        // Read all strings and make a node point to each string
        for (int i = 0; i < N; i++) {
            String word = io.getWord();
            nodes[i] = new LinkedListNode(word);
        }
        
        // Merge strings
        for (int i = 0; i < N - 1; i++) {
            int a = io.getInt() - 1; // first string
            int b = io.getInt() - 1; // second string
            // get our curr node
            LinkedListNode currnode_a = nodes[a];

            // pointer to the last node of linkedlist so that we can link start of second string to end of first string
            while (currnode_a.next != null) {
                currnode_a = currnode_a.next;
            }
            currnode_a.next = nodes[b]; // Link the second string to the first string
            nodes[b] = null; //nodes[b] points to nothing now
        }

        // find the non-null node containing our strings
        LinkedListNode thenode = null;
        for (int i = 0; i < N; i++) {
            if (nodes[i] != null) {
                thenode = nodes[i];
                break;
            }
        }
        
        // put the strings together to output as one line
        StringBuilder result = new StringBuilder();
        LinkedListNode res_node = thenode;
        while (res_node != null) {
            result.append(res_node.value);
            res_node = res_node.next;
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