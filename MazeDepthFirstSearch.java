/* Author: Susie Mueller
 * Purpose: Project 7
 * Date: 11/20/23
 * File: AbstractMazeSearch.java
 */


// The purpose of the MaxDepthFirstSearch class is to search the Maze using the DFS algorithm. 
// A stack is the backbone of the algorithm. 
public class MazeDepthFirstSearch extends AbstractMazeSearch {

    // Initialize fields. 
    private LinkedList<Cell> stack; 


    // Constructor. 
    public MazeDepthFirstSearch(Maze maze) {
        super(maze); // Calls parent's class constructor. 
        stack = new LinkedList<Cell>();
    }


    // Implementation of abstract methods. 

    // Method returns the next Cell to explore.
    public Cell findNextCell() {
        if (!stack.isEmpty()) {
            return stack.pop(); 
        } else {
            return null;
        }
    }


    // Method adds the given Cell to whatever structure is storing the future Cells to explore.
    public void addCell(Cell next) {
        stack.push(next);
    }


    // Method returns the number of future Cells to explore (so just the size of whatever structure is storing the future Cells).
    public int numRemainingCells() {
        return stack.size();
    }  
}