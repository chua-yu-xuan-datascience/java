/* 
You and your group of friends often use nicknames to refer to each other. One day, you noticed that some of the nicknames could refer to more than one person, which causes confusion. For example, jo could refer to either john or joseph. Your task is to determine how many names can be matched with a given nickname.
A name and a nickname are said to match if and only if the nickname is a prefix of the name. In other words, given a name 
N of length lN and a nickname K of length  lK, N and K. match iff:
1. lN≥lK.
2. N[0]=K[0],  N[1]=K[1],…,N[lK−1]=K[lK−1].
You will be given a list of 
A names and B nicknames. For each nickname, print the number of names that match with it.
Input
The first line contains an integer, 
1≤A≤100000, the number of names.
The next A lines contain the names, each name on one line. Each name is between 1 and 10 characters long and consists of lowercase letters only. All names are unique.
The next line contains an integer, 1≤B≤100000, the number of nicknames.
The next B lines contain the nicknames, each nickname on one line. Each nickname is between 1 and 10 characters long and consists of lowercase letters only.
Output
For each nickname, print the number of names that it matches with. Output each integer on a separate line, in the same order as the input.
Subtasks
(40 Points):  A,B≤1000. All nicknames are exactly 1 character long.
(30 Points): A,B≤100000. All nicknames are exactly 1 character long.
(30 Points): No additional constraint.
Note: Sample Input 3 corresponds to Subtask 3. You can ignore it if you are only attempting Subtask 1 or 2.
Warning
The input files are large. Please use fast I/O methods.

/*
// make parent for nodes, try inserting number to test out tree, to rotate correctly
// hashmap (key = starting char, value = avl tree) to handle duplicate inputs
import java.io.*;
import java.util.*;

class AVLTree {
    private class Node {
        String name;
        Node left, right;
        int height;

        Node(String name) {
            this.name = name;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    private Node root;

    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private Node rightRotate(Node T) {
        Node w = T.left;
        T.left = w.right;
        w.right = T;

        T.height = Math.max(height(T.left), height(T.right)) + 1;
        w.height = Math.max(height(w.left), height(w.right)) + 1;

        return w;
    }

    private Node leftRotate(Node T) {
        Node w = T.right;
        T.right = w.left;
        w.left = T;

        T.height = Math.max(height(T.left), height(T.right)) + 1;
        w.height = Math.max(height(w.left), height(w.right)) + 1;

        return w;
    }

    private Node leftRightRotate(Node T) {
        T.left = leftRotate(T.left);
        return rightRotate(T);
    }

    private Node rightLeftRotate(Node T) {
        T.right = rightRotate(T.right);
        return leftRotate(T);
    }

    public void insert(String name) {
        root = insert(root, name);
    }

    private Node insert(Node T, String name) {
        if (T == null) return new Node(name);

        if (name.compareTo(T.name) < 0) {
            T.left = insert(T.left, name);
        } else if (name.compareTo(T.name) > 0) {
            T.right = insert(T.right, name);
        } else {
            return T; // No duplicates
        }

        T.height = Math.max(height(T.left), height(T.right)) + 1;
        int balance = getBalance(T);

        if (balance > 1 && name.compareTo(T.left.name) < 0) {
            return rightRotate(T);
        }
        if (balance < -1 && name.compareTo(T.right.name) > 0) {
            return leftRotate(T);
        }
        if (balance > 1 && name.compareTo(T.left.name) > 0) {
            return leftRightRotate(T);
        }
        if (balance < -1 && name.compareTo(T.right.name) < 0) {
            return rightLeftRotate(T);
        }

        return T;
    }

    public int countMatches(String nickname) {
        return countMatches(root, nickname);
    }
 

    // indexOf(nickname) ==0 
    private int countMatches(Node T, String nickname) {
        if (T == null) return 0;
    
        int count = 0;
    
        // If the current node's name starts with the nickname, it's a match
        if (matchesPrefix(T.name, nickname)) {
            count++;
        }
    
        // If the current node's name is lexicographically greater than the nickname,
        // there's no need to search the right subtree.
        if (nickname.compareTo(T.name) < 0) {
            count += countMatches(T.left, nickname);
        }
        // If the current node's name is lexicographically less than the nickname,
        // there's no need to search the left subtree.
        else if (nickname.compareTo(T.name) > 0) {
            count += countMatches(T.right, nickname);
        }
        // If the current node's name starts with the nickname, search both subtrees.
        // 1 + countmatch (left) + right
        else {
            count += countMatches(T.left, nickname);
            count += countMatches(T.right, nickname);
        }
    
        return count;
    }

    private boolean matchesPrefix(String name, String nickname) {
        if (nickname.length() > name.length()) {
            return false;
        }

        for (int i = 0; i < nickname.length(); i++) {
            if (name.charAt(i) != nickname.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}

public class Sweetnames {
    public static void main(String[] args) throws IOException {
        Kattio io = new Kattio(System.in, System.out);

        int A = io.getInt();
        AVLTree tree = new AVLTree();

        for (int i = 0; i < A; i++) {
            tree.insert(io.getWord());
        }

        int B = io.getInt();

        for (int i = 0; i < B; i++) {
            String nickname = io.getWord();
            io.println(tree.countMatches(nickname));
        }

        io.flush();
    }
}

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
*/

/*
import java.io.*;
import java.util.*;

public class Sweetnames {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        // HashMap to store duplicates to avoid redundant queries
        HashMap<String, Integer> duplicates = new HashMap<>();
        // HashMap to store AVL trees for each starting character
        HashMap<Character, AVLTree> trees = new HashMap<>();

        // Reading the number of names
        int A = io.getInt();
        for (int i = 0; i < A; i++) {
            String name = io.getWord();  // Using getWord() to read the name
            char firstLetter = name.charAt(0);
            AVLTree tree = trees.getOrDefault(firstLetter, new AVLTree());
            tree.insert(name);
            trees.put(firstLetter, tree);
        }

        // Reading the number of nicknames
        int B = io.getInt();
        for (int i = 0; i < B; i++) {
            String nickname = io.getWord();  // Using getWord() to read the nickname
            int matches;

            // Check if the nickname has been queried before
            if (duplicates.containsKey(nickname)) {
                matches = duplicates.get(nickname);
            } else {
                char firstLetter = nickname.charAt(0);
                AVLTree tree = trees.get(firstLetter);
                if (tree != null) {
                    matches = tree.countMatches(nickname);
                } else {
                    matches = 0;
                }
                duplicates.put(nickname, matches);
            }

            io.println(matches);  // Output the number of matches
        }

        io.close();
    }
}

class AVLTree {
    private class Node {
        String name;
        Node left, right, parent;
        int height;
        int size;

        Node(String name) {
            this.name = name;
            this.left = this.right = this.parent = null;
            this.height = 0;
            this.size = 1; // Each node starts with a size of 1 (itself)
        }
    }

    private Node root;

    AVLTree() {
        this.root = null;
    }

    // Helper method to check the height of a node
    private int height(Node node) {
        return node == null ? -1 : node.height;
    }

    // Helper method to get the size of a node (size of the subtree rooted at the node)
    private int size(Node node) {
        return node == null ? 0 : node.size;
    }

    // Helper method to update the size of a node
    private void updateSize(Node node) {
        if (node != null) {
            node.size = size(node.left) + size(node.right) + 1;
        }
    }

    // Helper method to update the height of a node
    private void updateHeight(Node node) {
        if (node != null) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }
    }

    // Insert a new name into the AVL tree
    public void insert(String name) {
        root = insert(root, name, null);
    }

    // Recursive insertion with parent pointer
    private Node insert(Node node, String name, Node parent) {
        if (node == null) {
            Node newNode = new Node(name);
            newNode.parent = parent;
            return newNode;
        }
        
        if (name.compareTo(node.name) < 0) {
            node.left = insert(node.left, name, node);
        } else {
            node.right = insert(node.right, name, node);
        }

        updateHeight(node);
        updateSize(node);
        return balance(node, name);
    }

    // Balance the tree after insertion
    private Node balance(Node node, String name) {
        int balanceFactor = height(node.left) - height(node.right);

        // Left heavy
        if (balanceFactor > 1) {
            if (name.compareTo(node.left.name) > 0) {
                node.left = leftRotate(node.left);
            }
            return rightRotate(node);
        }

        // Right heavy
        if (balanceFactor < -1) {
            if (name.compareTo(node.right.name) < 0) {
                node.right = rightRotate(node.right);
            }
            return leftRotate(node);
        }

        return node;
    }

    // Left rotation for AVL tree
    private Node leftRotate(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        if (newRoot.left != null) {
            newRoot.left.parent = node;
        }
        newRoot.left = node;
        newRoot.parent = node.parent;
        node.parent = newRoot;

        updateHeight(node);
        updateHeight(newRoot);
        updateSize(node);
        updateSize(newRoot);

        return newRoot;
    }

    // Right rotation for AVL tree
    private Node rightRotate(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        if (newRoot.right != null) {
            newRoot.right.parent = node;
        }
        newRoot.right = node;
        newRoot.parent = node.parent;
        node.parent = newRoot;

        updateHeight(node);
        updateHeight(newRoot);
        updateSize(node);
        updateSize(newRoot);

        return newRoot;
    }

    // Count matches for a nickname
    public int countMatches(String nickname) {
        return countMatches(root, nickname);
    }

    // Recursive method to count matches for a given nickname
    private int countMatches(Node node, String nickname) {
        if (node == null) {
            return 0;
        }

        int count = 0;

        // If the current node's name starts with the nickname
        if (node.name.indexOf(nickname) == 0) {
            count++;
        }

        // Traverse the left or right subtree based on the comparison
        if (nickname.compareTo(node.name) < 0) {
            count += countMatches(node.left, nickname);
        } else {
            count += countMatches(node.right, nickname);
        }

        return count;
    }
}
*/

import java.util.*;
import java.io.*;

public class Nicknames {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        // Maps for handling duplicates and AVL trees by the first letter
        HashMap<String, Integer> nicknameCounts = new HashMap<>();
        HashMap<Character, AVL> firstLetterTrees = new HashMap<>();

        // Reading the list of names and inserting them into corresponding AVL trees
        int numNames = io.getInt();
        for (int i = 0; i < numNames; i++) {
            String name = io.getWord();
            char firstChar = name.charAt(0);

            // If the tree for the first character exists, insert; otherwise, create a new tree
            firstLetterTrees.computeIfAbsent(firstChar, k -> new AVL()).insert(name);
        }

        // Process queries (nicknames) and check their validity
        int numQueries = io.getInt();
        for (int j = 0; j < numQueries; j++) {
            String query = io.getWord();
            int matchCount;

            // If the nickname has been queried before, use the cached result
            if (nicknameCounts.containsKey(query)) {
                matchCount = nicknameCounts.get(query);
            } else {
                char firstChar = query.charAt(0);
                matchCount = 0;

                // If there's an AVL tree for the first letter of the nickname, check it
                if (firstLetterTrees.containsKey(firstChar)) {
                    matchCount = firstLetterTrees.get(firstChar).checkValid(query);
                }
                // Cache the result
                nicknameCounts.put(query, matchCount);
            }

            // Output the number of valid matches for the nickname
            io.println(matchCount);
        }

        // Close the Kattio output stream
        io.close();
    }
}

// Vertex class represents a node in the AVL tree
class Vertex {
    Vertex parent, left, right;
    String key;
    int height, size;

    // Constructor to initialize the Vertex with a given key
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

    // Constructor for an empty AVL tree
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

        updateSize(node);
        updateHeight(node);
        return balance(node);
    }

    // Update the height of a node
    void updateHeight(Vertex node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    // Update the size of a node
    void updateSize(Vertex node) {
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
    Vertex balance(Vertex node) {
        int balanceFactor = height(node.left) - height(node.right);
        
        if (balanceFactor > 1) {
            if (height(node.left.left) < height(node.left.right)) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        } else if (balanceFactor < -1) {
            if (height(node.right.right) < height(node.right.left)) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        }
        
        return node;
    }

    // Rotate a node left to restore balance
    Vertex rotateLeft(Vertex node) {
        Vertex newRoot = node.right;
        node.right = newRoot.left;
        if (newRoot.left != null) newRoot.left.parent = node;
        newRoot.left = node;
        node.parent = newRoot;

        updateSize(node);
        updateSize(newRoot);
        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    // Rotate a node right to restore balance
    Vertex rotateRight(Vertex node) {
        Vertex newRoot = node.left;
        node.left = newRoot.right;
        if (newRoot.right != null) newRoot.right.parent = node;
        newRoot.right = node;
        node.parent = newRoot;

        updateSize(node);
        updateSize(newRoot);
        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    // Check if the nickname is valid based on the AVL tree structure
    int checkValid(String query) {
        Vertex highestValid = findHighestValid(root, query);
        if (highestValid == null) return 0;
        return 1 + checkLeft(highestValid.left, query) + checkRight(highestValid.right, query);
    }

    // Find the highest valid vertex in the tree
    Vertex findHighestValid(Vertex node, String query) {
        if (node == null) return null;
        if (node.key.startsWith(query)) return node;
        if (query.compareTo(node.key) < 0) return findHighestValid(node.left, query);
        return findHighestValid(node.right, query);
    }

    // Check the left subtree for valid matches
    int checkLeft(Vertex node, String query) {
        if (node == null) return 0;
        if (node.key.startsWith(query)) {
            return 1 + checkLeft(node.left, query) + size(node.right);
        }
        return checkLeft(node.right, query);
    }

    // Check the right subtree for valid matches
    int checkRight(Vertex node, String query) {
        if (node == null) return 0;
        if (node.key.startsWith(query)) {
            return 1 + checkRight(node.right, query) + size(node.left);
        }
        return checkRight(node.left, query);
    }
}
