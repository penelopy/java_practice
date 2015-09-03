import java.util.*;

class Solution {
  //use of 'String' below seems odd - but this is the standard line in the caller class - yes?
  public static void main(String[] args) {
  // System.out.println(Arrays.asList(args));  
  // this is a constructor
    World w = new World(30, 20);
    // calls the seed method to create/populate grid
    w.seedFirstGeneration();
    // prints current grid and generates new grid
    for (int i=0; i<10; i++) {
      w.print();
      w.computeNextGeneration();
    // }
  }
}

class World {
  // grid = [][]

  private boolean[][] grid;
  private boolean[][] nextGrid;
  //so in Java you must declare this before you use as parameters on line 26?
  private final int rows;
  private final int cols;
  //let's talk about the next 4 lines
  public World(int rows, int cols) {
    grid = new boolean[rows][cols];
    //does this look like [false, false, ...30 times][false, false, ...20 times]
    nextGrid = new boolean[rows][cols];
    //adding 'this' makes these variable accessible to other functions?
    this.rows = rows; //==30?
    this.cols = cols;
  }
  
  public void seedFirstGeneration() {
    // in Java you have to declare that you are using a built-in class?
    Random rand = new Random();
    //loops through 30 rows, and 20 cols (see line 7, 26)
    for (int row=0; row<rows; row++) {
      for (int col=0; col<cols; col++) {
        // pick a number between 0..9
        int i = rand.nextInt(10);
        if (i == 0) {
          grid[row][col] = true;
        }
      }
    }
  }
  
  public void print() {
    for (boolean[] row : grid) {
      //we don't need to declare 'cell' first - we can just create/name on-the-fly?
      for (boolean cell : row) {
        if (cell) {
          System.out.print("#");
        } else {
          System.out.print(".");
        }
      }
      System.out.println();
    }
    
    // Print some blank spaces after the grid.
    System.out.println();
    System.out.println();
  }
  
  // private int rows() {
  //   //grid is accessible without using 'this' after line 27?
  //   return grid.length;
  // }
  
  public int getLiveNeighborCount(int row, int col) {
    int liveNeighborCount = 0; 
    
    // let's talk about delta
    for (int dx=-1; dx<=1; dx++) {
      for (int dy=-1; dy<=1; dy++) {
        if (dx == 0 && dy == 0) {
          continue;
        }
        
        if (aliveAt(row + dy, col + dx)) {
          liveNeighborCount++;
        }
      }
    }
    
    return liveNeighborCount;
  }
  
  private boolean aliveAt(int row, int col) {
    // Since the caller added a delta to row and col, they may
    // go out of bounds. This wraps to keep in the grid.
    if (row < 0) {
      row = rows-1;
    } else if (row == rows) {
      row = 0;
    }
    
    if (col < 0) {
      col = cols - 1;
    } else if (col == cols) {
      col = 0;
    }
    
    return grid[row][col];
  }
  
  public void computeNextGeneration() {
    /*
    - Any live cell with fewer than two live neighbours 
      dies, as if caused by under-population.
    - Any live cell with two or three live neighbours 
      lives on to the next generation.
    - Any live cell with more than three live neighbours
      dies, as if by overcrowding.
    - Any dead cell with exactly three live neighbours
      becomes a live cell, as if by reproduction.
    */
    for (int row=0; row<rows; row++) {
      for (int col=0; col<cols; col++) {
        boolean alive = grid[row][col];
        int liveNeighbors = getLiveNeighborCount(row, col);
        
        boolean nextState = false;
        if (alive) {
          if (liveNeighbors < 2) {
            nextState = false;
          } else if (liveNeighbors < 4) {
            // 2-3 neighbors
            nextState = true;
          } else {
            // more than 3
            nextState = false;
          }
        } else {
          if (liveNeighbors == 3) {
            // reproduction
            nextState = true;
          } 
        }
        
        nextGrid[row][col] = nextState;
      }
    }    
    
    // Swap
    boolean[][] tmp = grid;  
    grid = nextGrid;
    nextGrid = tmp;
  }
}


class Maze
class Cell


(objects instead of primitives)


word search
secret santa