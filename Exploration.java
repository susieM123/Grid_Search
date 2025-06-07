/*
 * Author: Susie Mueller
 * Purpose: Project 7
 * Date: 11/28/23
 * File: Exploration.java
 */


// Imported Libraries
import java.util.Random;


// The purpose of the Exploration class is to analyze BFS, DFS, and A* Algorithms. 
// Notes on how to compile and run are at the bottom. 
public class Exploration {

    public static void main(String [] args) throws InterruptedException {

        int numTrials = 50; 
        int probCounter = 0; // Track num targets reached
        int lengthCounter = 0; // Track lengths of path
        int exploredCellsCounter = 0; // Track avg num cells explored


        // Runs multiple simulations
        for (int i = 0; i < numTrials; i++) { 

            // Create a maze obj
            Maze maze = new Maze(20, 20, 0.1); 
            MazeDepthFirstSearch dfs = new MazeDepthFirstSearch(maze);
            // MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            // MazeAStarSearch astar = new MazeAStarSearch(maze);

            // Setting start and target of search
            Random rand = new Random(); 
            Cell start = maze.get(rand.nextInt(maze.getRows()), rand.nextInt(maze.getCols())); 
            Cell target = maze.get(rand.nextInt(maze.getRows()), rand.nextInt(maze.getCols())); 

            while (target.getType() == CellType.OBSTACLE) { 
                target = maze.get(rand.nextInt(maze.getRows()), rand.nextInt(maze.getCols()));
            }

            LinkedList<Cell> list = dfs.search(start, target, true, 0);
            // LinkedList<Cell> list = bfs.search(start, target, true, 0);
            // LinkedList<Cell> list = astar.search(start, target, true, 0);

            if (list != null) {
                probCounter += 1;
                // System.out.println("Length of Path: " + list.size());
                lengthCounter += list.size();
            }

            int count = 0;
            for (Cell cell : maze) {
                if (cell.getPrev() != null){
                    count++;
                }
            }
            exploredCellsCounter += count;
        }
        System.out.println("\nProbability of Reaching Target: " + probCounter/numTrials * 100 + "%");
        // System.out.println("Average Length of Path: " + lengthCounter/numTrials);
        // System.out.println("Average Cells Explored: " + exploredCellsCounter/100);
    }
}


/* 
 * How to Compile: javac Exploration.java
 * 
 * How to Run: java Exploration 
 * 
 */
