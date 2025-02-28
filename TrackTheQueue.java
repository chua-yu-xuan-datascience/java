import java.util.*;
import java.io.*;

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

// Linkedlist get is O(n) while array adding in front is O(n)
// Making our own circular array can help make front and back addition and removal O(1) while having accessing at O(1)
class CircularArrayQueue {
    int[] array;
    int front;
    int back;
    int capacity;
    int size;

    public CircularArrayQueue(int capacity) {
        this.capacity = capacity;
        this.array = new int[capacity];
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }
    
    public void addFront(int value) {
        front = (front - 1 + capacity) % capacity;
        array[front] = value;
        size++;
    }

    public void addBack(int value) {
        array[back] = value;
        back = (back + 1) % capacity;
        size++;
    }

    public int removeFront() {
        int value = array[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    public int removeBack() {
        back = (back - 1 + capacity) % capacity;
        int value = array[back];
        size--;
        return value;
    }

    public int get(int index) {
        return array[(front + index) % capacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

public class TrackTheQueue {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        int Q = io.getInt(); // Number of operations
        int K = io.getInt(); // K value- maximum number of people in front of a member to be inserted
 
        // Array for more direct accessing (get)
        // but need to split into two queues along K so that member can be done in O(1)
        CircularArrayQueue firstQueue = new CircularArrayQueue(1000000); // Make capacity larger
        CircularArrayQueue secondQueue = new CircularArrayQueue(1000000); // Large enough
 
        // Operations to perform
        for (int i = 0; i < Q; i++) {
            String op = io.getWord();  //Read operation type

            if (op.equals("queue")) {
                // Add x to back of queue
                int x = io.getInt();
                // but if firstQueue has less than K people, add to the back of firstQueue
                if (firstQueue.size() < K) {
                    firstQueue.addBack(x);
                } else {
                    secondQueue.addBack(x);
                }

            } else if (op.equals("member")) {
                // Add x at position K+1
                int x = io.getInt();
                // but if firstQueue has less than K people, add to the back of firstQueue
                if (firstQueue.size() < K) {
                    firstQueue.addBack(x);
                // Else, add to the front of secondQueue (true K+1)
                } else {
                    secondQueue.addFront(x);
                }

            } else if (op.equals("vip")) {
                // Add x to front of queue
                int x = io.getInt();
                // but if firstQueue already has K people, remove last person and add to front of secondQueue to keep firstQueue size K
                if (firstQueue.size() == K) {
                    secondQueue.addFront(firstQueue.removeBack());
                }
                firstQueue.addFront(x);

            } else if (op.equals("slower")) {
                K++;
                // if queue has more than K people (more importantly there is someone in secondQueue)
                // Remove first person from secondQueue to add to back of firstQueue to expand firstQueue size to bigger K
                if (!secondQueue.isEmpty()) {
                    firstQueue.addBack(secondQueue.removeFront());
                }

            } else if (op.equals("faster")) {
                K--;
                // if queue has more than (smaller) K people
                // Remove last person from firstQueue to add to front secondQueue to reduce firstQueue size to smaller K
                if (firstQueue.size() > K) {
                    secondQueue.addFront(firstQueue.removeBack());
                }

            } else if (op.equals("findID")) {
                int position = io.getInt();
                // If the position is within the firstQueue (which we know size is max K), get directly from firstQueue
                if (position <= K) {
                    io.println(firstQueue.get(position - 1));
                } else {
                    // else, minus number of people from firstQueue(K) to get position in secondQueue
                    io.println(secondQueue.get(position - K - 1));
                }
            }
        }

        io.close();
    }
}