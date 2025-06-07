/* Author: Susie Mueller
 * Purpose: Project 7
 * Date: 11/21/23
 * File: MazeAStarSearch.java
 */


// The purpose of the MazeAStarSearch class is to search the Maze using the A* algorithm. 
// A PriorityQueue is the backbone of the algorithm. 
public class MazeAStarSearch extends AbstractMazeSearch {
    
    private PriorityQueue<Cell> priorityQueue;

    // Constructor. 
    public MazeAStarSearch(Maze maze) {
        super(maze);
        priorityQueue = new Heap<Cell>(this::compare); // Pass the comparator to the Heap constructor. 
    }


    // Comparator for A* algorithm tells the heap how to prioritize Cells since Cells aren't innately Comparable.. 
    // Returns a negative number if cell1 is of greater priority than cell2, a positive number if cell2 is of greater priority, or 0 if they have the same priority.
    public int compare(Cell cell1, Cell cell2) {
        int priority1 = cell1.getStepsToReach() + cell1.estimatedDistanceTo(getTarget());
        int priority2 = cell2.getStepsToReach() + cell2.estimatedDistanceTo(getTarget());

        return Integer.compare(priority1, priority2);
    }


    // Method returns the next Cell to explore.
    public Cell findNextCell(){
        return priorityQueue.poll(); 
    }


    // Method adds the given Cell to whatever structure is storing the future Cells to explore.
    public void addCell(Cell next){
        priorityQueue.offer(next);
    }


    // Method returns the number of future Cells to explore (so just the size of whatever structure is storing the future Cells).
    public int numRemainingCells(){
        return priorityQueue.size(); 
    }
}
