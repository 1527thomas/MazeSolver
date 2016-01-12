import java.util.Stack;
import java.util.Random;
import java.util.ArrayList;
import java.io.BufferedWriter;

/** This class describes a maze that consists of a set of cells. The constructor allows you
  * to create a specific maze, by specifiying a maze ID.  */
public class Maze {
  //2-D array contains all the cells in the maze
  private Cell mazeCells[][];
  
  //actual and display sizes of the maze
  private int width;
  private int displayWidth;
  private int height;
  private int displayHeight;
  
  //starting and ending positions
  private Cell startCell;
  private Cell endCell;
  
  //char used to represent the wall
  public static final char WALL = '#';
  
  /************************************
    * Constructor. Creates a maze of size width x height
    * and initializes the cell objects, and creates the maze with
    * the given mazeID.
    * @param width The number of cells in the horizontal direction
    * @param heigth The number of cells in the vertical direction
    * @param mazeId if this is > 0 it will generate the maze with that id
    *         so it will be the same maze as before. If it is <= 0
    *               it will generate a truly random maze.
    * @version 1.0
    * */
  public Maze(int width, int height, int mazeId) {
    this.width = width;
    this.height = height;
    this.displayWidth = (2*width)+1;
    this.displayHeight = (2*height)+1;
    
    mazeCells = new Cell[height][width];
    
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
        mazeCells[i][j] = new Cell(j,i);
      }
    }
    
    generateMaze(mazeId);
  }
  
  /****************************************
    * This method clears the display of any character
    * information.
    * 
    * @param mazeDisplay a 2-D character array that contains the visual
    *           display of the maze
    * @version 1.0
    * */
  private void clearMazeDisplay(char[][] mazeDisplay)
  {
    for(int i = 0; i < displayWidth; i++){
      for(int j = 0; j < displayHeight; j++){
        mazeDisplay[i][j] = ' ';
      }
    }
  }
  
  /************************************
    * This method creates the 2-D character array that represents
    * the display of the maze. Because you can only draw one character
    * at a position in the output but you need to be able to draw walls 
    * between cells, it draws a larger version of the maze to
    * convey all information. This is why when you specify
    * a 20x6 maze for example, the output is actually 41x13. This
    * array is organized so the column is specified first, and then
    * the row, e.g., a[col][row].
    * 
    * @return char[][] a 2-D array that contains the visual display of
    *                  the maze
    * @version 1.0
    * */
  public char[][] getMazeDisplay() {
    
    char mazeDisplay[][];
    
    mazeDisplay = new char[displayWidth][displayHeight];
    
    clearMazeDisplay(mazeDisplay);
    
    for(int i = 0; i < displayWidth; i++) {
      mazeDisplay[i][0] = WALL;
    }
    for(int i = 0; i < displayHeight; i++) {
      if(i > 1) {
        mazeDisplay[0][i] = WALL;
      }
    }
    
    int currentDisplayX = 1;
    int currentDisplayY = 1;
    
    for(int currentRow = 0; currentRow < height; currentRow++) {
      for(int currentCol = 0; currentCol < width; currentCol++) {
        char[][] cellDisp = mazeCells[currentRow][currentCol].printCell();
        for(int i = 0; i < 2; i++) {
          for(int j = 0; j < 2; j++) {
            mazeDisplay[currentDisplayX+j][currentDisplayY+i] = cellDisp[i][j];
          }
        }
        currentDisplayX +=2;
      }
      currentDisplayX = 1;
      currentDisplayY += 2;
    }
    
    mazeDisplay[0][1] = 'S';
    mazeDisplay[displayWidth-1][displayHeight-2] = 'E';
    return mazeDisplay;
  }
  
  /********************************
    * Prints the 2-D visual display of the maze to the
    * terminal
    * 
    * @param mazeDisplay a 2-D char array containing the display of
    *                    the maze
    * @version 1.0
    * */
  public void printMaze(char[][] mazeDisplay) {
    
    for(int y = 0; y < displayHeight; y++) {
      for(int x = 0; x < displayWidth; x++) {
        System.out.print(mazeDisplay[x][y]);
      }
      System.out.println();
    }
  }
  
  /*******************************
    * Prints the maze object by calling getMazeDisplay()
    * and passing it to the print method
    * 
    * @version 1.0
    * */
  public void printMaze() {
    printMaze(getMazeDisplay());
  }
  
  /********************************
    * This method is used for debugging. It prints out the
    * cell information of every maze in the cell. You will
    * not need to use this to complete the assignment
    * 
    * @version 1.0
    * */
  private void printMazeInfo() {
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++) {
        System.out.println("i="+ i + ",j=" + j + ": " + mazeCells[i][j]);
      }
    }
  }
  
  /***********************************
    * Returns the Cell object at coordinates (row,col)
    * 
    * @param row the row of the cell you want
    * @param col the column of the cell you want
    * @return Cell the Cell object located at those coordinates
    * @version 1.0
    * */
  private Cell getCell(int row, int col) {
    return(mazeCells[row][col]);
  }
  
  
  /************************************
    * Checks to see if a coordinate pair (row,col) is inside the
    * bounds of the maze.
    * 
    * @param row the row of the cell you want
    * @param col the column of the cell you want
    * @return true if the coordiate pair is inside the maze, false
    *      otherwise
    * @version 1.0
    * */
  public boolean inBounds(int row, int col) {
    if (col >= 0 & col < width && row >= 0 && row < height)
      return true;
    else
      return false;
  }
  
  /*************************************
    * Returns a collection of all the cells that are connected
    * to currentCell. If there is a wall between currentCell and
    * it's neighbor it is not added to this collection.
    * 
    * @param currentCell the Cell whose neigbors you want
    * @return ArrayList a collection of all the cells neighboring currentCell
    * @version 1.0
    * */
  public ArrayList<Cell> getNeighbors(Cell currentCell) {
    ArrayList<Cell> neighbors = new ArrayList<Cell>();
    
    if (!currentCell.hasNorthWall())
      neighbors.add(mazeCells[currentCell.getRow() - 1][currentCell.getCol()]);
    if(!currentCell.hasEastWall())
      neighbors.add(mazeCells[currentCell.getRow()][currentCell.getCol()+1]);
    if(!currentCell.hasSouthWall())
      neighbors.add(mazeCells[currentCell.getRow() + 1][currentCell.getCol()]);
    if(!currentCell.hasWestWall())
      neighbors.add(mazeCells[currentCell.getRow()][currentCell.getCol()-1]);
    return(neighbors);
  }
  
  /***************************************
    * This method is used by the generateMaze method. It returns
    * a collection of all Cells that are neigbors to currentCell
    * that still have all their walls intact. You will not need
    * to use or modify this method for the assignment.
    * 
    * @param currentCell the Cell whose neighbors with walls you want
    * @return ArrayList<Cell> a collection of the neighbors with walls intact
    * @version 1.0
    * */
  private ArrayList<Cell> getNeigborsWithWalls(Cell currentCell) {
    ArrayList<Cell> neighbors = new ArrayList<Cell>();
    if(inBounds(currentCell.getRow() - 1, currentCell.getCol())) {
      if(mazeCells[currentCell.getRow() - 1][currentCell.getCol()].areAllWallsIntact())
        neighbors.add(mazeCells[currentCell.getRow() - 1][currentCell.getCol()]);
    }
    
    if(inBounds(currentCell.getRow() + 1, currentCell.getCol())) {
      if(mazeCells[currentCell.getRow() + 1][currentCell.getCol()].areAllWallsIntact())
        neighbors.add(mazeCells[currentCell.getRow() + 1][currentCell.getCol()]);
    }
    
    if(inBounds(currentCell.getRow(), currentCell.getCol() - 1)) {
      if(mazeCells[currentCell.getRow()][currentCell.getCol() - 1].areAllWallsIntact())
        neighbors.add(mazeCells[currentCell.getRow()][currentCell.getCol() - 1]);
    }
    
    if(inBounds(currentCell.getRow(), currentCell.getCol() + 1)) {
      if(mazeCells[currentCell.getRow()][currentCell.getCol() + 1].areAllWallsIntact())
        neighbors.add(mazeCells[currentCell.getRow()][currentCell.getCol() + 1]);
    }
    
    return(neighbors);
  }  
  
  
  /*************************
    * This method generates a random Maze. It uses the following algorithm
    * to generate the maze. You will not need to modify this function for
    * the assignment.
    * 
    * Maze Generation Algorithm:
    * 
    * create a CellStack (LIFO) to hold a list of cell locations 
    * set TotalCells = number of cells in grid 
    * choose a cell at random and call it CurrentCell 
    * set VisitedCells = 1 
    * 
    * while VisitedCells < TotalCells 
    *       find all neighbors of CurrentCell with all walls intact  
    *       if one or more found 
    *             choose one at random 
    *             knock down the wall between it and CurrentCell 
    *             push CurrentCell location on the CellStack 
    *             make the new cell CurrentCell 
    *             add 1 to VisitedCells 
    *     else 
    *             pop the most recent cell entry off the CellStack 
    *             make it CurrentCell 
    *    endIf 
    * 
    * endWhile  
    * @param mazeID if this is > 0 it will generate the maze with that id
    *         so it will be the same maze as before. If it is <= 0
    *               it will generate a truly random maze.
    * @version 1.0
    * */
  private void generateMaze(int mazeID) {
    Stack<Cell> CellStack = new Stack<Cell>();
    
    int TotalCells = width * height;
    
    Cell currentCell;
    ArrayList<Cell> neighbor_cells;
    
    Random numGenerator;
    if(mazeID > 0) {
      numGenerator = new Random(mazeID);
    }
    else {
      numGenerator = new Random();
    }
    int start_x = numGenerator.nextInt(width);
    int start_y = numGenerator.nextInt(height);
    currentCell = mazeCells[start_y][start_x];
    
    int VisitedCells = 1;
    
    while(VisitedCells < TotalCells) {
      neighbor_cells = getNeigborsWithWalls(currentCell);
      if(neighbor_cells.size() > 0) {
        Cell selected = neighbor_cells.get(numGenerator.nextInt(neighbor_cells.size()));
        
        if(selected.getCol() == currentCell.getCol()) {
          if(selected.getRow() > currentCell.getRow()) {
            currentCell.breakSouthWall();
            selected.breakNorthWall();
          }
          else {
            currentCell.breakNorthWall();
            selected.breakSouthWall();
          }
        }
        else if(selected.getRow() == currentCell.getRow()) {
          if(selected.getCol() > currentCell.getCol()) {
            currentCell.breakEastWall();
            selected.breakWestWall();
          }
          else {
            currentCell.breakWestWall();
            selected.breakEastWall();
          }
        }
        
        CellStack.push(currentCell);
        currentCell = selected;
        VisitedCells++;
      }
      else {
        currentCell = CellStack.pop();
      }
    }
    
    startCell = mazeCells[0][0];
    endCell = mazeCells[height-1][width-1];
    
  }
  
  /***************
    * Returns the start Cell of the maze
    * 
    * @return Cell the starting cell of the maze
    * @version 1.0
    * */
  public Cell getStartCell() {
    return startCell;
  }
  
  /**************
    * Returns the end Cell of the maze
    * 
    * @return Cell the ending cell of the maze
    * @version 1.0
    * */
  public Cell getEndCell() {
    return endCell;
  }
}