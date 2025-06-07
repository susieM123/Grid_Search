/* Author: Susie Mueller
 * Purpose: Project 7
 * Date: 11/13/23
 * File: Heap.java
 */


// Libraries used. 
import java.util.Comparator;
import java.util.HashSet;
import java.util.NoSuchElementException;


// The purpose of the Heap class is to build a Heap structure.
// We must implement an interface so Heap can act as Priority Queue. 
// Priority Queue is a type of Queue that uses a Comparator to poll (remove) the item of greatest priority
public class Heap<T> implements PriorityQueue<T> {
    
    // Initializes Heap fields. 
    private Comparator<T> comparator; 
    private int size; 
    private Node<T> root; 
    private Node<T> last;
    HashSet <Node<T>> nodeMap; 


    // Container class.
    // Node class represents a node in the binary heap.
    private class Node<T> {
        Node<T> left, right, parent; 
        T data; 

        // Node constructor.
        Node(T data, Node<T> parent) {
            this.data = data; 
            this.parent = parent;
        }
    }


    // Primary heap constructor.
    @SuppressWarnings("unchecked")
    public Heap(Comparator<T> comparator, boolean maxHeap) {
        if (comparator != null) {
            // If no comparator is provided, it defaults to natural ordering.
            this.comparator = comparator;
        } else {
            // If comparator is null, assume T is Comparable and create a default comparator.
            this.comparator = new Comparator<T>() {

                @Override
                public int compare(T o1, T o2) {
                    return ((Comparable<T>) o1).compareTo(o2);
                }

            };
        }
        // Adjust comparator for maxHeap.
        if (maxHeap) {
            this.comparator = new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return Heap.this.comparator.compare(o2, o1);
                }
            };
        }
        // Initialize other fields.
        size = 0;
        root = null;
        last = null;
    }

    // Constructor creates a min-heap with comparator.  
    public Heap(Comparator<T> comparator) {
        this(comparator, false);
    }

    // Constructor without comparator. 
    public Heap(boolean maxHeap) {
        this(null, maxHeap);
    }

    // Constructor creates a min-heap without comparator.
    public Heap() {
        this(null, false);
    }

    // Method swaps the data between these two nodes. 
    private void swap(Node<T> node1, Node<T> node2) {
        T temp = node1.data;
        node1.data = node2.data; 
        node2.data = temp; 
    }

    // Method to bubble a Node up the heap. 
    private void bubbleUp(Node<T> curNode) {
        if (curNode == root) {
            return;
        }
        T curNodeData = curNode.data;
        T parentData = curNode.parent.data;
        // Checks if current node's data is smaller than it's parent's data
        if (comparator.compare(curNodeData, parentData) < 0) {
            swap(curNode, curNode.parent); 
            bubbleUp(curNode.parent);
        }
    }

    // Method to bubble a Node down the heap.
    private void bubbleDown(Node<T> curNode) {
        if (curNode.left == null)
            return;
        else if (curNode.right == null) {
            if (comparator.compare(curNode.data, curNode.left.data) > 0) {
                // If the right child is null, compare and swap with left if necessary
                swap(curNode, curNode.left);
                bubbleDown(curNode.left);
            }
        } else {
            if (comparator.compare(curNode.left.data, curNode.right.data) < 0) {
                if (comparator.compare(curNode.data, curNode.left.data) > 0) {
                    // Compare and swap with left if its data is smaller than the current node's data.                    
                    swap(curNode, curNode.left);
                    bubbleDown(curNode.left);
                }
            } else {
                if (comparator.compare(curNode.data, curNode.right.data) > 0) {
                    // Compare and swap with right if its data is smaller than the current node's data.
                    swap(curNode, curNode.right);
                    bubbleDown(curNode.right);
                }
            }
        }
    }

    // Returns the number of items in the Heap. 
    public int size() {
        return size; 
    }

    // Returns the item of highest priority in the Heap. 
    public T peek() {
        if (root != null) {
            return root.data;
        }
        return null; 
    }

    // Adds the specified item into the Heap. 
    public void offer(T item) {
        if (size == 0) {
            // If the heap is empty, create a new root node.
            root = new Node<>(item, null);
            last = root;
            size++;
            return;
        }
        if (size % 2 == 0) {
            // If the size is even, insert to the right of the last node.
            last.parent.right = new Node<>(item, last.parent);
            last = last.parent.right;
        } else {
            Node<T> curNode = last;
            // Traverse to find the next insertion point.
            while (curNode != root && curNode == curNode.parent.right) {
                curNode = curNode.parent;
            }
            if (curNode != root) {
                curNode = curNode.parent.right;
            }
            while (curNode.left != null) {
                curNode = curNode.left;
            }
            // Insert to the left of the found node.
            curNode.left = new Node<>(item, curNode);
            last = curNode.left;

        }
        // Maintain the heap property by bubbling up.
        bubbleUp(last);
        size++;
    }


    // Returns and removes the item of highest priority. 
    public T poll() {
        if (size == 0) {
            // If the heap is empty, throw an exception.
            throw new NoSuchElementException();
        }
        T output;

        if (size == 1) {
            // If there is only one element, remove it and update root and last.
            output = last.data;
            root = null;
            last = null;
            size--;
        } else {
            output = root.data;
            root.data = last.data; 

            if (size % 2 == 0) {
                // If size is even, find the node to remove and update last.
                Node<T> curNode = last;
                while (curNode != root && curNode == curNode.parent.left) {
                    curNode = curNode.parent;
                }
                if (curNode != root) {
                    curNode = curNode.parent.left;
                }

                while (curNode.right != null) {
                    curNode = curNode.right;
                }

                if (last.parent.left == last) {
                    last.parent.left = null;
                }

                last = curNode;
                bubbleDown(root);
            } else {
                if (last.parent.right == last) {
                    last.parent.right = null;
                }
                last = last.parent.left;
                bubbleDown(root);
            }
            size--;
        }
        return output;
    }

    // Updates the priority of the given item. 
    public void updatePriority(T item) {
        Node<T> node = find(root, item); // Uses helper method to locate given node
        if (node == null) {
            return;
        }
        // Updates node's data then uses bubble methods to keep heap property
        node.data = item;
        bubbleUp(node);
        bubbleDown(node);
    }

    // Helper method recursively searches for a node with data equal to specified item.
    private Node<T> find(Node<T> curNode, T item) {
        if (curNode == null) {
            return null;
        }
        if (curNode.data.equals(item)) {
            return curNode;
        }
        Node<T> leftResult = find(curNode.left, item);
        if (leftResult != null) {
            return leftResult;
        }
        Node<T> rightResult = find(curNode.right, item);
        if (rightResult != null) {
            return rightResult;
        }
        return null;
    }
}