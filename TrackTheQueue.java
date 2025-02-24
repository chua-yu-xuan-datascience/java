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

public class TrackTheQueue {

    // Node class for LinkedList
    static class LinkedListNode {
        int id;
        LinkedListNode next;
        LinkedListNode prev;

        public LinkedListNode(int id) {
            this.id = id;
            this.next = null;
            this.prev = null;
        }
    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        int Q = io.getInt(); // Number of operations
        int K = io.getInt(); // K value- maximum number of people in front of a member to be inserted

        // just label head and tail first to create empty list
        LinkedListNode head = new LinkedListNode(-1); // head
        LinkedListNode tail = new LinkedListNode(-1); // tail
        head.next = tail;
        tail.prev = head;

        // Operations to perform
        for (int i = 0; i < Q; i++) {
            String op = io.getWord();  // Read operation type

            if (op.equals("queue")) {
                // add x to the back of the queue
                int x = io.getInt();
                LinkedListNode newNode = new LinkedListNode(x);
                // Add the new node at the end of the list
                newNode.next = tail; // newnode point forward to tail
                newNode.prev = tail.prev; // newnode point backward to tail prev
                // only do last 2 moves after new node points to tail prev and tail, if not will lose them
                tail.prev.next = newNode; //tail prev point forward to newnode 
                tail.prev = newNode; // tail point backward to newnode
                

            } else if (op.equals("member")) {
                // Add x at position K+1
                int x = io.getInt();
                LinkedListNode newNode = new LinkedListNode(x);
                
                // Find the K+1-th position (like the tail earlier)
                LinkedListNode current = head.next;
                int size = 0;
                while (current != tail && size < K) {
                    current = current.next;
                    size++;
                }

                // Insert the new member at the K+1 position 
                newNode.next = current; // newnode point forward to (original) K+1 node
                newNode.prev = current.prev; // newnode point backward to K-th node
                // only do last 2 moves after new node points to K and original K+1, if not will lose them
                current.prev.next = newNode; // K-th node point forward to new node
                current.prev = newNode; // (original) K+1 node point backward to new node
                
                
            } else if (op.equals("vip")) {
                // Add x to the front of the queue
                int x = io.getInt();
                LinkedListNode newNode = new LinkedListNode(x); 
                newNode.next = head.next; // new node point forwards to head next
                newNode.prev = head; // new node point backwards to head
                // only do last 2 moves after new node points to head and head next, if not will lose them
                head.next.prev = newNode; // head next point backwards to new node
                head.next = newNode; // head point forward to new node
                
            } else if (op.equals("slower")) {
                K++;

            } else if (op.equals("faster")) {
                K--;

            } else if (op.equals("findID")) {
                // Print the ID at the given position
                int pos = io.getInt();
                LinkedListNode current = head.next;
                // need the number of times we need current to move
                while (pos-- > 1) {
                    current = current.next;
                }
                io.println(current.id); // Output the person at given position's ID
            }
        }

        io.close();
    }
}



/*
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

public class TrackTheQueue {
    public static void main(String[] args) {
        // Kattio initialization for fast input and output
        Kattio io = new Kattio(System.in, System.out);

        // Read number of operations and initial K
        int Q = io.getInt();  // Number of operations
        int K = io.getInt();  // Initial value of K

        // Queue to store the IDs of people
        Deque<Integer> queue = new ArrayDeque<>();
        
        // Process each operation
        for (int i = 0; i < Q; i++) {
            String op = io.getWord();  // Read the operation type
            
            if (op.equals("queue")) {
                // queue x: Add x to the back of the queue
                int x = io.getInt();
                queue.addLast(x);
            } else if (op.equals("member")) {
                // member x: Add x to the position K+1 (or less if less than K people)
                int x = io.getInt();
                // Insert after the first K elements, or at the end if there are fewer than K people
                int position = Math.min(K, queue.size());
                List<Integer> temp = new ArrayList<>(queue);
                temp.add(position, x);
                queue = new ArrayDeque<>(temp);
            } else if (op.equals("vip")) {
                // vip x: Add x to the front of the queue
                int x = io.getInt();
                queue.addFirst(x);
            } else if (op.equals("slower")) {
                // slower: Increase K
                K++;
            } else if (op.equals("faster")) {
                // faster: Decrease K
                K--;
            } else if (op.equals("findID")) {
                // findID pos: Print the ID of the person at position pos
                int pos = io.getInt();
                // Find the person at position pos (1-based index)
                List<Integer> temp = new ArrayList<>(queue);
                io.println(temp.get(pos - 1)); // Output the ID at the requested position
            }
        }
        
        // Close the Kattio input/output
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