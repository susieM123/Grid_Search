/* Author: Susie Mueller
 * Purpose: Project 7 
 * Date: 11/20/23
 * File: MazeBreadthFirstSearch.java
 */


// The purpose of the MazeBreadthFirstSearch class is to search the maze using the BFS algorithm. 
// A queue is the backbone of the algorithm. 
public class MazeBreadthFirstSearch extends AbstractMazeSearch {

    // Initialize fields. 
    private LinkedList<Cell> queue; 

    // Constructor. 
    public MazeBreadthFirstSearch(Maze maze) {
        super(maze); 
        queue = new LinkedList<Cell>();
    }

    
    // Implementation of abstract methods. 

    // Method returns the next Cell to explore.
    public Cell findNextCell() {
        return queue.poll();
    }


    // Method adds the given Cell to whatever structure is storing the future Cells to explore.
    public void addCell(Cell next) {
        queue.offer(next);
    }


    // Method returns the number of future Cells to explore (so just the size of whatever structure is storing the future Cells).
    public int numRemainingCells() {
        return queue.size();
    }
}
