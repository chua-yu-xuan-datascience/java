import java.io.*;
import java.util.*;

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

public class TrackTheQueue {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        int Q = io.getInt(); // Number of operations
        int K = io.getInt(); // K value - maximum number of people in front of a member to be inserted

        // Array for more direct accessing
        ArrayList<Integer> queue = new ArrayList<>();

        // Operations to perform
        for (int i = 0; i < Q; i++) {
            String op = io.getWord();  // Read operation type

            if (op.equals("queue")) {
                // Add x to the back of the queue
                int x = io.getInt();
                queue.add(x);

            } else if (op.equals("member")) {
                // Add x at position K+1 (K in 0-index)
                // special case: if queue size less than K, then add at the end, whichever is smaller
                int x = io.getInt();
                int position = Math.min(queue.size(), K);
                queue.add(position, x); 

            } else if (op.equals("vip")) {
                // Add x to the front of the queue
                int x = io.getInt();
                queue.add(0, x);

            } else if (op.equals("slower")) {
                K++;

            } else if (op.equals("faster")) {
                K--;

            } else if (op.equals("findID")) {
                // Print the ID at the given position (1-indexed)
                int position = io.getInt();
                io.println(queue.get(position - 1));
            }
        }

        io.close();
    }
}





/*
try changing linkedlist to array for direct accessing
import java.util.*;
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

public class TrackTheQueue {
    public static void main(String[] args) {

        Kattio io = new Kattio(System.in, System.out);

        int Q = io.getInt(); // Number of operations
        int K = io.getInt(); // K value- maximum number of people in front of a member to be inserted

        // Use a LinkedList for efficient adding than using nodes
        LinkedList<Integer> queue = new LinkedList<>();

        // Operations to perform
        for (int i = 0; i < Q; i++) {
            String op = io.getWord();  // Read operation type

            if (op.equals("queue")) {
                // Add x to the back of the queue
                int x = io.getInt();
                queue.addLast(x);

            } else if (op.equals("member")) {
                // Add x at position K+1 (K in 0-index)
                // special case: if queue size less than K, then add at the end, whichever is smaller
                int x = io.getInt();
                int position = Math.min(queue.size(), K);
                queue.add(position, x);

            } else if (op.equals("vip")) {
                // Add x to the front of the queue
                int x = io.getInt();
                queue.addFirst(x);

            } else if (op.equals("slower")) {
                K++;

            } else if (op.equals("faster")) {
                K--;

            } else if (op.equals("findID")) {
                // Print the ID at the given position
                int position = io.getInt();
                io.println(queue.get(position - 1));
            }
        }

        io.close();
    }
}
*/


/*
Tom is selling some handmade items but demand for them has become very popular! 
Hundreds of people are queuing to order the items. Tom requests your help to write a program to 
keep track of people in the queue. Each person has a unique integer ID.

While people usually join the back of the queue, some are treated as VIPs and join the front of the queue. 
Tom also has a membership system in which a member can be slotted into the queue after the first K people 
(or less, if the queue has less than K people). 
Note that VIPs and members do not have their position fixed – 
Subsequent VIPs and/or members can push the positions of existing ones further back.

Your program needs to support the following operations which can be performed in any order:

queue x : person with ID x joins the back of the queue

member x : person with ID x joins the queue, now having (K+1)th position (or less if there were <K people in front)

vip x : person with ID x joins the queue, now having 1st position, pushing all other people in the queue back

slower : K is increased by one, so members join the queue further back

faster : K is decreased by one, so members wait less

findID pos : print the ID of the person at the position pos in the queue, on a new line

It is guaranteed that no one will join the queue more than once, that K will never be less than 1 at all times, 
and that there will be a person at the position queried.

Input
The first line contains 2 integers Q (1 <= Q <=800,000) the number of operations to perform, and 
K (1 <= K <=  900,000) the maximum number of people in front of a member who is about to join the queue.

Each of the next Q lines contains one of the six operations described above. Each ID is a positive integer 
(1 <ID < 10^6)

Output
One line for each findID pos command. Each line contains a person ID.

Operations:

queue x: Add person x to the back of the queue.
member x: Insert person x at position K+1 (or the end if there are fewer than K people).
vip x: Insert person x at the front (position 1).
slower: Increase K, meaning members will join later in the queue.
faster: Decrease K, meaning members will join earlier in the queue.
findID pos: Find and return the person at position pos.

and when we add from the front, must go -> this way
 */