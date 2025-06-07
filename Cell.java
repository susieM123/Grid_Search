/* Author: Susie Mueller
 * Purpose: Project 7 
 * Date: 11/19/23
 * File: Cell.java
 */


// Libraries used. 
import java.awt.Color;
import java.awt.Graphics;


// The purpose of this class is to represent each cell on a 2D grid.
// Each cell will have an underlying type, either an unobstrocted cell (free cell) or an obstructed cell.
public class Cell {

    // Initialize fields.
    private int row, col; // Specifies the row and column of this Cell
    private Cell prev; // Specifies the Cell which, when explored, revealed this Cell for the first time
    private CellType type; // Specifies the CellType of this Cell (either FREE or OBSTACLE)
    private boolean visited; // Determines whether we've visited this Cell yet
    private int stepsToReach; // Number of steps taken to reach this cell

    /**
     * Constructs a Cell from the given parameters.
     * @param r  the row of the Cell
     * @param c  the column of the Cell
     * @param type the CellType of the Cell (either FREE or OBSTACLE)
     */
    public Cell(int r, int c, CellType type) {
        row = r;
        col = c;
        this.type = type;
        this.visited = false; 
    }

    /* 
     * Returns boolean whether we've visited this Cell yet. 
     */
    public boolean visited() {
        return this.visited; 
    }

    /*
     * Sets the previous Cell of this to the given {@code Cell prev}.
     * This means that when {@code prev} was explored, this Cell was found for the
     * first time.
     * @param prev the previous Cell of this one.
     */
    public void setPrev(Cell prev) {
        this.prev = prev;
    }


    /**
     * Returns the previous Cell of this one.
     * @return the previous Cell of this one.
     */
    public Cell getPrev() {
        return prev;
    }


    /**
     * Resets this Cell back to its initial state (which just sets prev to null)
     */
    public void reset() {
        setPrev(null);
    }


    /**
     * Returns the CellType of this Cell (either FREE or OBSTACLE).
     * @return the CellType of this Cell (either FREE or OBSTACLE).
     */
    public CellType getType() {
        return type;
    }


    /**
     * Returns the row of this Cell.
     * @return the row of this Cell.
     */
    public int getRow() {
        return row;
    }


    /**
     * Returns the column of this Cell.
     * @return the column of this Cell.
     */
    public int getCol() {
        return col;
    }


    /* 
     * Returns true if the object exists. 
     */
    public boolean equals(Object o) {
        if (!(o instanceof Cell))
            return false;
        Cell c = (Cell) o;
        return row == c.row && col == c.col && type == c.type;
    }


    /*
     * Returns the number of steps it took to reach this cell.
     */
    public int getStepsToReach() {
        return stepsToReach; 
    }


    /*
     * Returns an estimate of the distance from this cell to the target cell. 
     */
    public int estimatedDistanceTo(Cell target) {
        int dx = Math.abs(this.getCol() - target.getCol());
        int dy = Math.abs(this.getRow() - target.getRow());
        return (int) Math.sqrt(dx * dx + dy * dy); 
    }

    /* 
     * Sets visited to true and prev to c. 
     */
    public void visitFrom(Cell c) {
        this.visited = true; 
        this.prev = c; 
    }
    

    /*
     * toString method.
     */
    public String toString() {
        return "(" + row + ", " + col + ", " + type + ")";
    }


    /**
     * Draws this Cell to the given Graphics object.
     * If this Cell is FREE, then it will be drawn with yellow if it has been
     * visited, otherwise gray.
     * If this Cell is an OBSTACLE, it will be drawn black.
     * @param g     the Graphics object on which to draw.
     * @param scale the scale at which to draw this Cell.
     */
    public void drawType(Graphics g, int scale) {
        g.setColor(Color.BLACK);
        g.drawRect(getCol() * scale, getRow() * scale, scale, scale);
        switch (getType()) {
            case FREE:
                draw(g, scale, getPrev() != null ? Color.YELLOW : Color.GRAY);
                break;
            case OBSTACLE:
                draw(g, scale, Color.BLACK);
                break;
        }
    }


    /**
     * Recursively draws lines from each visited Cell to the Cell that they revealed
     * by exploration.
     * @param maze  the Maze in which this Cell resides.
     * @param g     the Graphics object on which to draw.
     * @param scale the scale at which to draw.
     * @param c     the Color to draw with.
     */
    public void drawAllPrevs(Maze maze, Graphics g, int scale, Color c) {
        g.setColor(c);
        for (Cell neighbor : maze.getNeighbors(this)) {
            if (neighbor.getPrev() == this) {
                g.drawLine(getCol() * scale + scale / 2, getRow() * scale + scale / 2,
                        neighbor.getCol() * scale + scale / 2, neighbor.getRow() * scale + scale / 2);
                neighbor.drawAllPrevs(maze, g, scale, c);
            }
        }
    }


    /**
     * Recursively draws lines from this Cell until reaching a Cell whose
     * {@code prev} is {@code null} or itself.
     * @param g     the Graphics object on which to draw.
     * @param scale the scale by which to draw.
     * @param c     the Color to drawn with.
     */
    public void drawPrevPath(Graphics g, int scale, Color c) {
        g.setColor(c);
        if (getPrev() != null && getPrev() != this) {
            g.drawLine(getCol() * scale + scale / 2, getRow() * scale + scale / 2,
                    getPrev().getCol() * scale + scale / 2, getPrev().getRow() * scale + scale / 2);
            getPrev().drawPrevPath(g, scale, c);
        }
    }

    
    /**
     * Draws this Cell onto the given Graphics object with the given Color.
     * @param g     the Graphics object on which to draw.
     * @param scale the scale by which to draw.
     * @param c     the Color to draw with.
     */
    public void draw(Graphics g, int scale, Color c) {
        g.setColor(c);
        g.fillRect(getCol() * scale + 2, getRow() * scale + 2, scale - 4, scale - 3);
    }
}