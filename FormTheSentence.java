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
            lastNode[i] = nodes[i]; // initially, the only node is also the lastnode in the list
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