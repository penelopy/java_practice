import java.io.*;
import java.util.*;

class Solution {
  public static void main(String[] args) {
    Maze maze = new Maze(15, 20);
    maze.gen2();
    // maze.generate();
    maze.render();
  }
}

class Maze {
  private final int rows;
  private final int cols;
  private Cell[][] cells;

  public Maze(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    
    
    cells = new Cell[rows][cols];
      
    for (int row=0; row<rows; row++){
      for (int col=0; col<cols; col++){
        cells[row][col] = new Cell(row, col);
      }
    }
  }
    
  
  public void generate() {
    Random rand = new Random();
    
    Cell currentCell = cells[rand.nextInt(rows)][rand.nextInt(cols)];
    
    generate(currentCell);
  }
  
  private void generate(Cell currentCell) {
    currentCell.visited = true;

    List<Cell> neighbors = getNeighbors(currentCell);
    
    for (Cell neighbor : neighbors) {
      if (!neighbor.visited) {
        removeWalls(currentCell, neighbor);
        generate(neighbor);
      }
    }
  }
  
  public void gen2() {
    Stack<Cell> stack = new Stack<Cell>();
    stack.push(cells[0][0]);
    
    while (!stack.isEmpty()) {
      Cell current = stack.peek();      
      Cell next = chooseNext(current);
      if (next != null) {
        next.visited = true;
        stack.push(next);
        removeWalls(current, next);
      } else {
        stack.pop();
      }
    }
  }
  
  private Cell chooseNext(Cell a) {
    List<Cell> neighbors = getNeighbors(a);
    for (Cell n : neighbors) {
      if (!n.visited) {
        return n;
      }
    }
    return null;
  }

  private void removeWalls(Cell a, Cell b) {
    
    //    b
    //  b a b
    //    b
    
    
    if (a.row > b.row) {
      a.up = false;
      b.down = false;
    }
    else if (a.col > b.col) {
      a.left = false;
      b.right = false;
    }
    else if (a.row < b.row) {
      a.down = false;
      b.up = false;
    }
    else {
      a.right = false;
      b.left = false;
    }
  }
  
  private List<Cell> getNeighbors(Cell currentCell) {    
    List<Cell> neighbors = new ArrayList<Cell>();
    
    int r = currentCell.row;
    int c = currentCell.col;
    
    // North
    if (r > 0) {
      neighbors.add(cells[r-1][c]);
    }
    
    // West
    if (c > 0) {
      neighbors.add(cells[r][c-1]);
    }
    
    // South
    if (r < rows - 1) {
      neighbors.add(cells[r+1][c]);
    }
    
    // East
    if (c < cols - 1) {
      neighbors.add(cells[r][c+1]);
    }
    
    Collections.shuffle(neighbors);
    
    return neighbors;
  }
  
  public void render() {
    // +--+--+--+
    // |  |  |  |
    // +--+--+--+
    // |  |  |  |
    // +--+--+--+
    
    char[][] canvas = new char[(rows * 2) + 1][(cols * 3) + 1]; 
    for (int r=0; r<canvas.length; r++) {
      for (int c=0; c<canvas[0].length; c++) {
        canvas[r][c] = ' ';
      }
    }
    
    for (Cell[] row : cells) {
      for (Cell cell : row) {
        cell.paint(canvas);
      }
    }
    
    // print the output array to System.out
    for (char[] row : canvas) {
      System.out.println(String.valueOf(row));
    }
  }
}

class Cell {
  final int row;
  final int col;
  
  boolean visited = false;
  boolean up = true;
  boolean right = true;
  boolean down = true;
  boolean left  = true;
  
  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
  }
  
  public void paint(char[][] canvas) {
    // 
    // +--+
    // |  |
    // +--+
    
    int x = col * 3; 
    int y = row * 2;

    // Paint the corners.
    canvas[y][x] = '+';
    canvas[y][x + 3] = '+';
    canvas[y + 2][x] = '+';
    canvas[y + 2][x + 3] = '+';
    
    // Paint the top edge of this cell.
    if (up) {
      canvas[y][x +1] = '-';
      canvas[y][x +2] = '-';
    }
    if (down) {
      canvas[y + 2][x +1] = '-';
      canvas[y + 2][x +2] = '-';      
    }
    if (left) {
      canvas[y+1][x] = '|';
    }
    if (right) {
      canvas[y+1][x+3] = '|';
    }
  }
  
  
}