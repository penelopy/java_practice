
/*

  
  7 columns, 5 rows:
  
  starting state:
  
  ##.....
  .....##
  ...#.##
  ....###
  .......

  what would the next state be?

  .......
  ....###
  .......
  .......
  .......
  

*/

import java.util.*;

class Solution {
  public static void main(String[] args) {
    // w is an instance (aka "object") of the World class
    World w = new World(30, 20);
    w.seedFirstGeneration();
    
    for (int i=0; i<10; i++) {
      w.print();
      w.computeNextGeneration();
    }
  }
}

class World {
  
  private boolean[][] grid;
  private boolean[][] nextGrid;
  
  private final int rows;
  private final int cols;
  
  public World(int rows, int cols) {
    grid = new boolean[rows][cols];
    nextGrid = new boolean[rows][cols];
    this.rows = rows;
    this.cols = cols;
  }
  
  public void seedFirstGeneration() {
    Random rand = new Random();
    
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
  
  private int rows() {
    return grid.length;
  }
  
  public int getLiveNeighborCount(int row, int col) {
    int liveNeighborCount = 0; 
    
    // y y y
    // y x y
    // y y y
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