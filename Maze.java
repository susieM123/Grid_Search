// Originally written by Colby College CS Professors.


// Libraries used. 
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Random;
import java.util.LinkedList;


// The purpose of the Maze class is to manage a grid of Cells. 
public class Maze implements Iterable<Cell> {

    /**
     * An iterator which iterates through all the Cells in the Maze row by row and
     * column by column.
     */
    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            int r, c;

            public boolean hasNext() {
                return r < getRows();
            }

            public Cell next() {
                Cell next = get(r, c);
                c++;
                if (c == getCols()) {
                    r++;
                    c = 0;
                }
                return next;
            }
        };
    }


    // Initialize fields.
    private int rows, cols; // The number of rows and columns in this Maze.
    private double density; // The density of this Maze. Each Cell independently has probability {@code density} of being an OBSTACLE.
    private Cell[][] landscape; // The 2-D array of Cells making up this Maze.


    /**
     * Constructs a Maze with the given number of rows and columns. Each Cell
     * independently has probability {@code density} of being an OBSTACLE.
     * @param rows    the number of rows.
     * @param columns the number of columns.
     * @param density the probability of any individual Cell being an OBSTACLE.
     */
    public Maze(int rows, int columns, double density) {
        this.rows = rows;
        this.cols = columns;
        this.density = density;
        landscape = new Cell[rows][columns];
        reinitialize();
    }


    /**
     * Initializes every Cell in the Maze.
     */
    public void reinitialize() {
        Random rand = new Random();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                landscape[r][c] = new Cell(r, c, rand.nextDouble() < density ? CellType.OBSTACLE : CellType.FREE);
            }
        }
    }


    /**
     * Calls {@code reset} on every Cell in this Maze.
     */
    public void reset() {
        for (Cell cell : this)
            cell.reset();
    }


    /**
     * Returns the number of rows in the Maze.
     * @return the number of rows in the Maze.
     */
    public int getRows() {
        return rows;
    }


    /**
     * Returns the number of columns in the Maze.
     * @return the number of columns in the Maze.
     */
    public int getCols() {
        return cols;
    }


    /**
     * Returns the Cell at the specified row and column in the Maze.
     * @param row the row
     * @param col the column
     * @return the Cell at the specified row and column in the Maze.
     */
    public Cell get(int row, int col) {
        return landscape[row][col];
    }


    /**
     * Returns a LinkedList of the non-OBSTACLE Cells neighboring the specified
     * Cell.
     * @param c the Cell to explore around.
     * @return a LinkedList of the non-OBSTACLE Cells neighboring the specified
     *         Cell.
     */
    public LinkedList<Cell> getNeighbors(Cell c) {
        LinkedList<Cell> cells = new LinkedList<Cell>(); // Creates a linked list of cells
        
        if (c != null) {
            
            int[][] steps = new int[][] { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } }; // Creates landscapes

            for (int[] step : steps) {

                int nextRow = c.getRow() + step[0];
                int nextCol = c.getCol() + step[1];

                if (nextRow >= 0 && nextRow < getRows() && nextCol >= 0 && nextCol < getCols()) {
                    Cell neighbor = get(nextRow, nextCol);

                    if (neighbor != null && neighbor.getType() != CellType.OBSTACLE)
                        cells.addLast(neighbor);
                    }
                }
            }

        return cells;
    }


    // toString method
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("-".repeat(cols + 3) + "\n");
        for (Cell[] cells : landscape) {
            output.append("| ");
            for (Cell cell : cells) {
                output.append(cell.getType() == CellType.OBSTACLE ? 'X' : ' ');
            }
            output.append("|\n");
        }
        return output.append("-".repeat(cols + 3)).toString();
    }


    /**
     * Calls {@code drawType} on every Cell in this Maze.
     * @param g
     * @param scale
     */
    public void draw(Graphics g, int scale) {
        for (Cell cell : this)
            cell.drawType(g, scale);
    }


    public static void main(String[] args) {
        Maze ls = new Maze(7, 7, .2);
        System.out.println(ls);
    }
}