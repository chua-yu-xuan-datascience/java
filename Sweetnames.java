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

public class Sweetnames {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        // HashMap to store the AVL trees by the first character
        HashMap<Character, AVL> firstCharTrees = new HashMap<>();

        // Reading the list of (full) names and inserting them into corresponding AVL trees
        int A = io.getInt();
        for (int i = 0; i < A; i++) {
            String fullname = io.getWord();
            char firstChar = fullname.charAt(0);

            // If the tree for the first character exists, insert; otherwise, create a new tree
            firstCharTrees.computeIfAbsent(firstChar, k -> new AVL()).insert(fullname);
        }

        // Process nicknames and check their validity
        int B = io.getInt();
        for (int j = 0; j < B; j++) {
            String nickname = io.getWord();
            int matchCount = 0;

            // If there's an AVL tree for the first letter of the nickname, check it
            char firstChar = nickname.charAt(0);
            if (firstCharTrees.containsKey(firstChar)) {
                matchCount = firstCharTrees.get(firstChar).countMatches(nickname);  // Use the optimized countMatches method with upper and lower bound
            }

            io.println(matchCount);
        }
        io.close();
    }
}

// Vertex class in the AVL tree
class Vertex {
    Vertex parent, left, right;
    String key;
    int height, size;

    // Constructor to initialize the Vertex
    Vertex(String key) {
        this.key = key;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.height = 0;
        this.size = 1;
    }
}

// AVL Tree class to store names in lexicographical order
class AVL {
    Vertex root;

    // empty AVL tree
    AVL() {
        this.root = null;
    }

    // Insert a new string into the AVL tree
    void insert(String value) {
        this.root = insert(this.root, value);
    }

    // Recursive insertion method
    Vertex insert(Vertex node, String value) {
        if (node == null) return new Vertex(value);

        if (value.compareTo(node.key) < 0) {
            node.left = insert(node.left, value);
            node.left.parent = node;
        } else {
            node.right = insert(node.right, value);
            node.right.parent = node;
        }

        newSize(node);
        newHeight(node);
        return rebalance(node);
    }

    // Update the height of a node
    void newHeight(Vertex node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    // Update the size of a node
    void newSize(Vertex node) {
        if (node != null) {
            node.size = 1 + size(node.left) + size(node.right);
        }
    }

    // Get the height of a node
    int height(Vertex node) {
        return node == null ? -1 : node.height;
    }

    // Get the size of a node
    int size(Vertex node) {
        return node == null ? 0 : node.size;
    }

    // Balance the tree after insertion to maintain AVL property
    Vertex rebalance(Vertex node) {
        int balanceFactor = height(node.left) - height(node.right);
        
        if (balanceFactor > 1) {
            if (height(node.left.left) < height(node.left.right)) {
                node.left = rotateLeft(node.left); // LEFT RIGHT imbalance
            }
            node = rotateRight(node); // LEFT LEFT imbalance
        } else if (balanceFactor < -1) {
            if (height(node.right.right) < height(node.right.left)) {
                node.right = rotateRight(node.right); // RIGHT LEFT imbalance
            }
            node = rotateLeft(node); // RIGHT RIGHT imbalance
        }
        
        return node;
    }

    // left rotation
    Vertex rotateLeft(Vertex node) {
        Vertex newRoot = node.right;
        node.right = newRoot.left;
        if (newRoot.left != null) newRoot.left.parent = node;
        newRoot.left = node;
        node.parent = newRoot;

        newSize(node);
        newSize(newRoot);
        newHeight(node);
        newHeight(newRoot);

        return newRoot;
    }

    // right rotation
    Vertex rotateRight(Vertex node) {
        Vertex newRoot = node.left;
        node.left = newRoot.right;
        if (newRoot.right != null) newRoot.right.parent = node;
        newRoot.right = node;
        node.parent = newRoot;

        newSize(node);
        newSize(newRoot);
        newHeight(node);
        newHeight(newRoot);

        return newRoot;
    }

    // Method to count the number of matches using lower and upper bound approach
    int countMatches(String nickname) {
        return countMatches(root, nickname);
    }

    // Recursive method 
    private int countMatches(Vertex node, String nickname) {
        if (node == null) {
            return 0;
        }

        // Find the lowest common ancestor (most highest part of tree where anything higher is not valid)
        Vertex lca = findLCA(nickname, node);
        if (lca == null) {
            return 0;
        }

        // After finding the LCA, count matches in the left and right subtrees 
        return 1 + countLowerBound(lca.left, nickname) + countUpperBound(lca.right, nickname);
    }

    private Vertex findLCA(String nickname, Vertex node) {
        if (node == null) {
            return null;
        }
        String currentKey = node.key;

        // If the node key starts with the nickname, it's the LCA
        if (currentKey.startsWith(nickname)) {
            return node;
        }

        // Continue to narrow search
        if (nickname.compareTo(currentKey) < 0) {
            return findLCA(nickname, node.left);
        } else {
            return findLCA(nickname, node.right);
        }
    }

    // Count the number of matches in the lower bound (left subtree) of the LCA
    private int countLowerBound(Vertex node, String nickname) {
        if (node == null) {
            return 0;
        }
        String currentKey = node.key;

        // If the node key starts with the nickname, count this node and explore left and right subtrees
        if (currentKey.startsWith(nickname)) {
            return 1 + countLowerBound(node.left, nickname) + size(node.right); // Anything on right are guaranteed valid matches
        } else {
            return countLowerBound(node.right, nickname);
        }
    }

    // Count the number of matches in the upper bound (right subtree) of the LCA
    private int countUpperBound(Vertex node, String nickname) {
        if (node == null) {
            return 0;
        }
        String currentKey = node.key;

        // If the node key starts with the nickname, count this node and explore left and right subtrees
        if (currentKey.startsWith(nickname)) {
            return 1 + countUpperBound(node.right, nickname) + size(node.left); // Anything on left are guaranteed valid matches
        } else {
            return countUpperBound(node.left, nickname);
        }
    }
}
