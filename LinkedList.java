/* Author: Susie Mueller
 * Date: 11/13/23
 * Purpose: Project 7 
 * File: LinkedList.java
 */


// Libraries used.
import java.util.Iterator; 
import java.util.ArrayList; 


// The primary purpose of the LinkedList class is to manage items using Stack and/or Queue behavior. 
public class LinkedList<T> implements Queue<T> {

    private Node head; // First Node in the list
    private int size; // Number of Nodes in the list
    private Node tail; 
    

    // Container class. 
    private class Node {
        T data;  
        Node next;
    
        // A constructor that initializes next to null and the container field to item. 
        public Node(T data) {
            this.data = data; 
            next = null; 
        }

        // Returns the value of the container field.
        public T getData() {
            return this.data;
        }

        // Sets next to the given node.
        public void setNext(Node n) {
            next = n; 
        }

        // Returns the next field.
        public Node getNext() {
            return this.next;
        }

    }


    // Container class. 
    private class LLIterator implements Iterator<T> {
        private Node current;

        // The constructor for the LLIterator given the head of a list.
        public LLIterator(Node head) {
            this.current = head;
        }

        // Returns true if there are still values to traverse (if the current node reference is not null).
        public boolean hasNext() {
            return (this.current != null);
        }

        // Returns the next item in the list, which is the item contained in the current node. The method also needs to move the traversal along to the next node in the list.
        public T next() {
            T data = current.data;
            this.current = this.current.next;
            return data;
        }

        // Does nothing. Implementing this function is optional for an Iterator.
        public void remove() {
        }
    }


    // Constructor initializes the fields so it's an empty list
    public LinkedList() {
        head = null;
        tail = null; 
        size = 0;
    }    
    

    // Returns the head of the LinkedList
    public Node getHead() {
        return this.head;
    }


    // Stack Interface Implementation

    // Returns the number of items in the stack
    public int size() {
        int numNodes = 0;
        Node current = this.head;

        while (current != null) { // Iterates through LinkedList
            current = current.getNext(); // Moves to next Node
            numNodes++; // Increases numNodes by one
        }

        return numNodes;
    }

    
    // Returns the item on the top of the stack
    public T peek() {
        return head.getData();
    }


    // Removes and returns the item on the top of the stack 
    public T pop() {
        return remove();
    }


    // Adds the given item to the top of the stack
    public void push (T item) {
        add(item);
    }


    // Returns true if the list is empty, otherwise returns false
    public boolean isEmpty() {
        return (this.size == 0);
    }


    // Empties the list
    public void clear() {
        this.head = null;  // Removes head
        this.size = 0; // Sets LinkedList size to 0
    }


    // Add an item to the beginning of the LinkedList
    public void add(T item) {
        Node newNode = new Node(item); // Creates Node w/ item
        newNode.setNext(head); // Places newNode before head
        this.head = newNode; // Sets NewNode to head
        if (size == 0) {
            tail = newNode; 
        }
        size++; // Increases LinkedList size by one 
    }

    
    // Adds the given item to the beginning of the LinkedList
    public void addFirst(T item) {
        Node newNode = new Node(item); // Creates a new Node with the given item

        // Set the new node as the head and link it to the current head
        newNode.setNext(head);
        head = newNode;

        size++; // Increase the size of the LinkedList by one
    }


    // Adds the given item to the end of the LinkedList
    public void addLast(T item) {
        Node newNode = new Node(item); // Creates a new Node with the given item

        if (head == null) {
            // If the list is empty, set the new node as the head
            head = newNode;
        } else {
            // Iterate to the end of the list
            Node current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }

            // Set the new node as the next node of the last node in the list
            current.setNext(newNode);
        }
        size++; // Increase the size of the LinkedList by one
    }
    

    // Removes the first item of the list and returns it
    public T remove() {
        T value = head.getData(); // Assigns head data to value
        this.head = head.getNext(); // Updates head to be next Node
        if (size == 1) {
            tail = null; 
        }
        size--; // Decreases size of LinkedList
        return value;
    }


    // Returns the item specified by the given index
    public T get(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Index out of bounds.");
            return null; 
        }
        Node current = head; // Starts at head
        for (int i = 0; i < index; i++) { // Iterates list to specified index
            current = current.getNext(); // Moves to next Node
        }
        return current.getData(); // Returns element at specified index
    }


    // Inserts the item at the specified position in the list
    public void add(int index, T item) {
        if (index < 0 || index >= size) { // Checks if index is within bounds of LinkedList
            return;
        } else if (index == 0) { // Checks if index is zero
            add(item);
            return; // Stops the function call 
        }
        Node current = this.head;  // Starts at head

        for (int i = 0; i < index - 1; i++) { // Iterates through LinkedList
            current = current.getNext(); // Moves to next Node
        }

        Node newNode = new Node(item); 
        newNode.setNext(current.getNext()); // Sets newNode's next to current's next
        current.setNext(newNode); // Sets current Node's next to newNode 
        size++; // Increases size of LinkedList by one
    }


    // Removes the item at a specified position in the list and returns it
    public T remove(int index) {
        if (index == 0) {
            return remove(); 
        }
        if (index < 0 || index >= size) {
            System.out.println("Index out of bounds.");
            return null; 
        }
        Node current = this.head;

        for (int i = 0; i < index - 1; i++) {
            current = current.getNext(); 
        }
        
        Node toRemove = current.getNext();
        Node currentNewNext = current.getNext().getNext(); 
        current.setNext(currentNewNext);
        size--; 
        return toRemove.getData();        
    }


    /* Returns true if o is present in this life, otherwise method returns false.
     * If there is an item "data" in this list such that data.equals(o), then
     * method returns true.*/
    public boolean contains(Object o) {
        Node current = this.head; // Starts at list head

        while (current != null) { // Iterates until end of list is reached
            if (current.getData().equals(o)) { // Checks if current node is equal to o
                return true; // If element is found
            }
            current = current.getNext(); // Moves to the next node
        }
        return false; // If element is not found in the entire list
    }


    /* Returns true if o is a LinkedList that also contains  
     * the same items in the same order. */
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        // Checks if o is an instance of LinkedList
        if (!(o instanceof LinkedList)) { 
            return false; 
        }

        // Cast o to a LinkedList
        LinkedList<T> oAsALinkedList = (LinkedList<T>) o; 

        // Checks if sizes of two lists are equal
        if (oAsALinkedList.size() != this.size()) { 
            return false;
        }

        // Assigns heads of both lists to variables
        Node currentNode = oAsALinkedList.getHead();
        Node otherNode = this.getHead();

        // Iterates through both lists
        while (currentNode != null && otherNode != null) {
            if (!currentNode.getData().equals(otherNode.getData())) { // Compares data in current nodes
                return false; 
            }
            // Moves to next nodes in both lists
            currentNode = currentNode.getNext();
            otherNode = otherNode.getNext();
        }
        return true; // If loop completes, the lists are equal
    }


    // Returns a String representation of the list. 
    public String toString() {
        String result = ""; // Initializes empty string to store
        Node current = head; // Start at list head 

        for (int i = 0; i < this.size(); i++) { // Iterates through list
            result += current.getData() + " -> "; // Append current node's data to result
            current = current.getNext(); // Move to next node
        }
        return result;
    }  
    

    // Converts the LinkedList to an ArrayList with the items in the same order.
    public ArrayList<T> toArrayList() {
        ArrayList<T> arr = new ArrayList<T>();  // Creates new arrayList
        Node current = this.head; // Start at head of LinkedList
        
        for (int i = 0; i < size; i++) { // Iterates through LinkedList
            arr.add(current.getData()); // Adds each element to arrayList
            current = current.getNext(); // Moves to next node
        }
        return arr; // Returns arrayList with items from LinkedList
    }


    // Return a new LLIterator pointing to the head of the list
    public Iterator<T> iterator() {
        return new LLIterator(this.head);
    }


    // Adds the given item to the end of the queue
    public void offer(T item) {
        addLast(item);
    }


    // Returns and removes the items at the front of the queue
    public T poll() {
        return remove();
    }
}