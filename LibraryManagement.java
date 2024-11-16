import java.text.SimpleDateFormat;
import java.util.*;

class Student {
    String name;
    int id_no;
    String stream;
    String book1, book2;
    int issuedBookCount;

    // Constructor to initialize student data
    Student(String name, int id_no, String stream) {
        this.name = name;
        this.id_no = id_no;
        this.stream = stream;
        this.book1 = null; // Initialize to null (no book issued)
        this.book2 = null; // Initialize to null (no second book issued)
        this.issuedBookCount = 0; // No books issued initially
    }

    // Method to display student details
    void display() {
        System.out.println("\nName of Student: " + this.name);
        System.out.println("ID of Student: " + this.id_no);
        System.out.println("Stream of Student: " + this.stream);
        System.out.println("Books Issued: " + this.issuedBookCount);
        if (this.book1 != null) System.out.println("Book 1: " + this.book1);
        if (this.book2 != null) System.out.println("Book 2: " + this.book2);
    }
}

class Node {
    String key;
    Node left, right;

    public Node(String item) {
        key = item;
        left = null;
        right = null;
    }
}

class FinalDSA {
    Node root;

    FinalDSA() {
        root = null;
    }

    // Insert Book
    void insert(String key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, String key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }
        if (key.compareTo(root.key) < 0)
            root.left = insertRec(root.left, key);
        else if (key.compareTo(root.key) > 0)
            root.right = insertRec(root.right, key);
        else
            System.out.println("Error: Book already exists.");
        return root;
    }

    void deleteKey(String key) {
        root = deleteRec(root, key);
    }

    Node deleteRec(Node root, String key) {
        if (root == null)
            return root;
        if (key.compareTo(root.key) < 0)
            root.left = deleteRec(root.left, key);
        else if (key.compareTo(root.key) > 0)
            root.right = deleteRec(root.right, key);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    String minValue(Node root) {
        String minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    public boolean containsNode(String value) {
        return containsNodeRecursive(root, value);
    }

    private boolean containsNodeRecursive(Node current, String key) {
        if (current == null) {
            return false;
        }
        if (key.equalsIgnoreCase(current.key)) {
            return true;
        }
        return key.compareTo(current.key) < 0 ? containsNodeRecursive(current.left, key) : containsNodeRecursive(current.right, key);
    }

    void printInorder() {
        printInorder(root);
    }

    void printInorder(Node node) {
        if (node == null)
            return;
        printInorder(node.left);
        System.out.print(node.key + " ");
        printInorder(node.right);
    }

    void printTree() {
        printTreeRec(root, 0);
    }

    void printTreeRec(Node t, int space) {
        if (t == null)
            return;
        space += 5;
        printTreeRec(t.right, space);
        System.out.println();
        for (int i = 5; i < space; i++)
            System.out.print(" ");
        System.out.print("[" + t.key + "]");
        printTreeRec(t.left, space);
    }
}

public class LibraryManagement {
    static void selectionSort(Student arr[]) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++)
                if (arr[j].id_no < arr[minIdx].id_no)
                    minIdx = j;
            // Swap the found minimum element with the first element
            Student temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }

    static void displayStudents(Student arr[]) {
        for (int i = 0; i < arr.length; i++) {
            arr[i].display();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        FinalDSA tree = new FinalDSA();
        HashMap<String, Integer> hashMapping = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        // Taking number of students as input
        System.out.println("Enter number of students: ");
        int numStudents = input.nextInt();
        input.nextLine();  // Consume the newline character after the integer input

        Student[] array = new Student[numStudents];

        // Input student details
        for (int i = 0; i < numStudents; i++) {
            System.out.println("\nEnter details for Student " + (i + 1));
            System.out.print("Enter name: ");
            String name = input.nextLine();
            System.out.print("Enter student ID: ");
            int id_no = input.nextInt();
            input.nextLine();  // Consume the newline character after the integer input
            System.out.print("Enter stream: ");
            String stream = input.nextLine();
            array[i] = new Student(name, id_no, stream);
        }

        // Insert books into the library
        while (true) {
            System.out.println("\nEnter a book to add to the library (or type 'done' to finish): ");
            String book = input.nextLine();
            if (book.equalsIgnoreCase("done")) {
                break;
            }
            tree.insert(book);
        }

        // Issue books to students
        for (int i = 0; i < numStudents; i++) {
            System.out.println("\nEnter book to issue to " + array[i].name + " (or type 'none' if no book): ");
            String book1 = input.nextLine();
            if (!book1.equalsIgnoreCase("none")) {
                array[i].book1 = book1;
                array[i].issuedBookCount++;
            }
            if (array[i].issuedBookCount < 2) {
                System.out.println("Enter second book to issue to " + array[i].name + " (or type 'none' if no second book): ");
                String book2 = input.nextLine();
                if (!book2.equalsIgnoreCase("none")) {
                    array[i].book2 = book2;
                    array[i].issuedBookCount++;
                }
            }
        }

        // Display students and their books
        displayStudents(array);

        // Show available books in BST
        System.out.println("\nAvailable Books in Library (BST Inorder):");
        tree.printInorder();
        System.out.println();

        // Sort students by their ID number
        selectionSort(array);

        // Display sorted students
        System.out.println("\nSorted Students:");
        displayStudents(array);

        input.close(); // Close the scanner at the end of main method
    }
}
