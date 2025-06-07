/* Author: Susie Mueller
 * Purpose: Project 7 
 * Date: 11/20/23
 * File: AbstractMazeSearch.java
 */


// Libraries used. 
import java.awt.Graphics; 
import java.awt.Color; 


// The purpose of the AbstractMazeSearch class is to contain the algorithmic blueprint necessary to search a Maze. 
public abstract class AbstractMazeSearch {

    // Initialize fields
    private Maze maze; // a Maze object that will be the basis for the search 
    private Cell start; // the Cell which the search starts from 
    private Cell target; // the Cell which the search is trying to reach
    private Cell cur; // the Cell where the search is currently at
    

    /*
     * AbstractMazeSearch constructor. 
     */
    public AbstractMazeSearch(Maze maze) {
        this.maze = maze;
        this.start = null; 
        this.target = null; 
        this.cur = null; 
    }


    // 3 abstract methods the inherited class must implement:
    
    // Method returns the next Cell to explore.
    public abstract Cell findNextCell();

    // Method adds the given Cell to the structure that's storing the future Cells to explore.
    public abstract void addCell(Cell next);

    // Method returns the number of future Cells to explore (so just the size of whatever structure is storing the future Cells).
    public abstract int numRemainingCells();

    

    // Returns the underlying Maze.
    public Maze getMaze() {
        return maze; 
    }
    
    
    // Sets the given target to be the target of the search.
    public void setTarget(Cell target) {
        this.target = target;
    }


    // Returns the target of the search.
    public Cell getTarget() {
        return target;
    }


    // Sets the given cell to be the current location of the search.
    public void setCur(Cell cell) {
        this.cur = cell; 
    }


    // Returns the current Cell location of the search.
    public Cell getCur() {
        return cur;
    }


    //  Sets the given start to be the start of the search. Additionally, set start's prev to be itself 
    public void setStart(Cell start) {
        this.start = start; 
        start.setPrev(start);
    }


    // Returns the start of the search.
    public Cell getStart() {
        return start; 
    }


    // Sets the current, start, and target Cells to be null.
    public void reset() {
        cur = null; 
        start = null; 
        target = null; 
    }


    // Finds a path from the start to the specified cell by repeatedly following the prev path. Returns the path if found, otherwise returns null.
    public LinkedList<Cell> traceback(Cell cell) {
        Cell curCell = cell;
        LinkedList<Cell> path = new LinkedList<Cell>();

        while (curCell != null) {
            path.addFirst(curCell);
            if (curCell == start) {
                return path; // Completed the path from start to the specified cell
            }
            curCell = curCell.getPrev();
        }
        return null; // Couldn't find a path
    }


    // Method that does the seraching. 
    public LinkedList<Cell> search(Cell start, Cell target, boolean display, int delay) {
        setStart(start);
        setTarget(target);
        setCur(start);

        MazeSearchDisplay mazeDisplay = null; 
        // Create display obj based on display variable

        if (display) {
            mazeDisplay = new MazeSearchDisplay(this, 20);
        }

        addCell(start); // Adds the starting cell to the set of Cells to explore

        while (numRemainingCells() > 0) {
            
            setCur(findNextCell());
            if (getCur().equals(target)) {
                if (mazeDisplay != null) {
                    mazeDisplay.repaint();
                    try {Thread.sleep(delay);} 
                    catch (InterruptedException e) {};
                }
                return traceback(target);
            }
            for (Cell neighbor : getMaze().getNeighbors(getCur())) {
                if (neighbor.getPrev() == null) {
                    neighbor.setPrev(getCur());
                    addCell(neighbor);
                    if (mazeDisplay != null) {
                        mazeDisplay.repaint();
                        try {Thread.sleep(delay);} 
                        catch (InterruptedException e) {};
                    }
                    if (neighbor.equals(target)) {
                        return traceback(target);
                    }
                }
            }
        }
        return null;
    }


    // Draw method adds visualization to searches.
    public void draw(Graphics g, int scale) {
        // Draws the base version of the maze
        getMaze().draw(g, scale);
        // Draws the paths taken by the searcher
        getStart().drawAllPrevs(getMaze(), g, scale, Color.RED);
        // Draws the start cell
        getStart().draw(g, scale, Color.BLUE);
        // Draws the target cell
        getTarget().draw(g, scale, Color.RED);
        // Draws the current cell
        getCur().draw(g, scale, Color.MAGENTA);
    
        // If the target has been found, draws the path taken by the searcher to reach
        // the target sans backtracking.
        if (getTarget().getPrev() != null) {
            Cell traceBackCur = getTarget().getPrev();
            while (!traceBackCur.equals(getStart())) {
                traceBackCur.draw(g, scale, Color.GREEN);
                traceBackCur = traceBackCur.getPrev();
            }
            getTarget().drawPrevPath(g, scale, Color.BLUE);
        }
    }
}