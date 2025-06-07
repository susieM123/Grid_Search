/* 
 * Author: Susie Mueller
 * Purpose: Project 7
 * Date: 11/26/23
 * File: BFSTest.java
 */


// The purpose of the BFSTest class is to test the MazeBreadthFirstSearch class.
public class BFSTest {

    public static void main(String [] args) {
        
        // case 1: testing MazeBreadthFirstSearch()
        {
            // setup
            System.out.println("\nTesting MazeBreadthFirstSearch().");
            Maze maze = new Maze(5, 5, 0.2); 
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);

            // verify
            System.out.println(bfs.numRemainingCells() + " == 0");

            // test
            assert bfs.numRemainingCells() == 0 : "Error in MazeBreadthFirstSearch::MazeBreadthFirstSearch()";
        }


        // case 2: testing addCell(Cell next)
        {
            // setup
            System.out.println("\nTesting addCell(Cell next).");
            Maze maze = new Maze(10, 10, 0.1); 
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell cell = maze.get(1, 1);
            bfs.addCell((cell));

            // verify
            System.out.println(bfs.numRemainingCells() + " == 1");

            // test
            assert bfs.numRemainingCells() == 1 : "Error in MazeBreadthFirstSearch :: addCell(Cell next)";
        }

        
        // case 3: testing findNextCell()
        {
            // setup
            System.out.println("\nTesting findNextCell().");
            Maze maze = new Maze(10, 10, 0.1); 
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell cell1 = maze.get(1, 1);
            Cell cell2 = maze.get(2, 2);
            bfs.addCell(cell1);
            bfs.addCell(cell2);
  
            // verify
            System.out.println(bfs.findNextCell() + " == " + cell1);

            // test
            assert bfs.findNextCell() == cell1 : "Error in MazeBreadthFirstSearch::findNextCell()";

        }

        System.out.println("\nDone testing MazeBreadthFirstSearch!");
    }
}
