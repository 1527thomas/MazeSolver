import java.util.ArrayList;

/** This class describe a Cell in a maze. */
public class Cell {
 //information about the walls of this cell
  private boolean northWall = true;
  private boolean southWall = true;
  private boolean eastWall = true;
  private boolean westWall = true;
  
  //has the cell been visited
  private boolean visited = false;
 
  //location information about this cell.
  private int row;
  private int col;
  
  /***************************
   * Constructs the Cell and assigns it's (row, col) coordinate pair
   * 
   * @param row row of this Cell in the maze
   * @param col column of this cell in the maze
   * @version 1.0
   * */
  public Cell(int col, int row) {
    this.row = row;
    this.col = col;
  }
  
  /****************************
   * Returns the row of this Cell
   * 
   * @return int the row of this cell in the maze
   * @version 1.0
   * */
  public int getRow() {
    return(row);
  }
  
  /****************************
   * Returns the column of this cell
   * 
   * @return int the column of this cell in the maze
   * @version 1.0
   * */
  public int getCol() {
    return(col);
  }
  
  /****************************
   * Returns the row location in a character display where 
   * both cells and walls are printed as characters.
   * 
   * @return int the delta of the row
   * @version 1.0
   * */
  public int getDisplayRow() {
    return row*2 + 1;
  }

  /****************************
   * Returns the column location in a character display where
   * both cells and walls are printed as characters.
   * 
   * @return int the delta of the col
   * @version 1.0
   * */
  public int getDisplayCol() {
    return col*2 + 1;
  }
  
  /*****************************
   * Returns true if all walls are intact for this Cell.
   * This is used by the maze generation method
   * 
   * @return boolean true if all walls are intact, false otherwise
   * @version 1.0
   * */
  boolean areAllWallsIntact() {
    return(northWall && eastWall && southWall && westWall);
  }
  
  /******************************
   * Creates a returns a display version of this cell.
   * In order to represent the cell we need more than
   * on character. We can represent a cell with a 2x2 array
   * and place the walls or paths. We only need to do the
   * south and east walls because of the way a maze is laid out
   * For example, if a cell has an east wall that means the cell
   * to it's right has a west wall but we only need to print it once
   * 
   * @return char[][] a 2-D array containing the visual display of this
   *                  cell
   * @version 1.0
   * */
  char[][] printCell() {
    char display[][] = {{' ',' '},{' ',' '}};
    
    if(eastWall)
      display[0][1] = Maze.WALL;
    if(southWall)
      display[1][0] = Maze.WALL;
    display[1][1] = Maze.WALL;
    
    return(display);
  }
  
  /********************************
   * Returns the debug String representing this cell. This is used
   * by the printMazeInfo method
   * 
   * @return String debugging information of this cell
   * @version 1.0
   * */
  public String toString() {
    String display;
    display = "["+ row +"]["+ col+"]-N:" + northWall + " E: " + eastWall + " S: " + southWall + " W: " + westWall;
    return(display);
  }
  
  /**********************
   * Removes the west wall of this cell. It is used
   * by the maze generation routine
   * 
   * @version 1.0
   * */
  void breakWestWall() {
    westWall = false;
  }
  
  /**********************
   * Removes the east wall of this cell. It is used
   * by the maze generation routine
   * 
   * @version 1.0
   * */
  void breakEastWall() {
    eastWall = false;
  }
  
  /**********************
   * Removes the north wall of this cell. It is used
   * by the maze generation routine
   * 
   * @version 1.0
   * */
  void breakNorthWall() {
    northWall = false;
  }

  /**********************
   * Removes the south wall of this cell. It is used
   * by the maze generation routine
   * 
   * @version 1.0
   * */
  void breakSouthWall() {
    southWall = false;
  }
  
 /**********************
  * Returns if this Cell has a north wall
  * 
  * @return true if there is a wall, false otherwise
  * @version 1.0
  * */
  public boolean hasNorthWall() {
    return(northWall);
  }
  
  /**********************
  * Returns if this Cell has a east wall
  * 
  * @return boolean true if there is a wall, false otherwise
  * @version 1.0
  * */
  public boolean hasEastWall() {
    return(eastWall);
  }
  
  /**********************
  * Returns if this Cell has a south wall
  * 
  * @return boolean true if there is a wall, false otherwise
  * @version 1.0
  * */
  public boolean hasSouthWall() {
    return(southWall);
  }
  
  /**********************
  * Returns if this Cell has a west wall
  * 
  * @return boolean true if there is a wall, false otherwise
  * @version 1.0
  * */
  public boolean hasWestWall() {
    return(westWall);
  }
  
  /***********************
   * Compares two cells and determines if they
   * are the same cell. This is accomplished simply
   * by comparing the row and col coordinate pair
   * 
   * @param o a Cell object to compare this Cell to
   * @return boolean true if they are equal, false otherwise
   * @version 1.0
   * */
  public boolean equals(Object o) {
    if (o instanceof Cell) {
      Cell c = (Cell)o;
      if (row == c.row && col == c.col)
        return true;
      else
        return false;
    }
    else
      return false;
  }
}