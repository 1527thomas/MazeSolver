/**
 * CSE 17
 * Stephen Friedman
 * saf217
 * Program #4 		DEADLINE: November 19, 2013
 * 
 * This program using the maze class creates a maze, uses a recursive method to solve the maze,
 * and finally the solved maze is printed to the screen. The algorithm for the recursive is explained 
 * in detail in the javadoc comment for its method. This program does solve the maze, however it also 
 * in the solution has a few rare spots where incorrect cells are filled in as "solution cells" even
 * though these cells are not correct in the solution. These cells only occur in pairs and I can not
 * figure out why they occur. I do know that these cells are in the correct solution array that is passed
 * into printSolvedMaze() yet I cannot figure out why these cells are being considered correct.
 */
import java.util.ArrayList;
public class MazeSolver {
	/**
	 * This method takes in a maze then uses the recursive form of this method to create and return
	 * the correct solution arrayList of the maze. In this method the 3 arraylists necessary are created,
	 * the solution list, the path list, and the all Cells visited list. The solution arraylist is equal to the 
	 * path array. Visited arrayList is a list of all the Cells the computer visited when trying to solve the maze.
	 * @param currentMaze the maze to solve
	 * @return the arrayList containing the correct path of Cells to take to complete the maze
	 */
	public static ArrayList<Cell> findPath(Maze currentMaze)
	{
		ArrayList<Cell> solution=new ArrayList<Cell>();
		ArrayList<Cell> path=new ArrayList<Cell>();
		ArrayList<Cell> visited=new ArrayList<Cell>();
		solution=findPath(currentMaze,currentMaze.getStartCell(),path,visited);
		return solution;
	}
	/**
	 * This method recursively uses an algorithm to find the correct path of Cells needed to complete the maze.
	 * The algorithm is broken down into 3 sections. Each time the computer goes to a cell  (current) it analyzes
	 * the neighboring spots around the cell to see which are walls, and which are empty movable-to spots.
	 * An arrayList called options contains all the neighboring Cells that are not walls. If options has all cells
	 * that have not been visited, then current is added to path, and current becomes the first Cell in options.
	 * If at least one of the options has not been visited, then current is added to path and the first unvisited 
	 * spot in options becomes current. If all of the options have been visited, path removes current (if it has current)
	 * and current becomes the option that was visited longest ago. After any of these 3 instances, a recursive call is
	 * made with the appropriate parameters. In the end this method returns the path that is the correct solution to the maze.
	 * @param currentMaze the maze to be solved
	 * @param current the current Cell the computer is analyzing
	 * @param path the correct solution (so far)
	 * @param visited is the list of all visited spots
	 * @return the path that is the solution to the maze
	 */
	public static  ArrayList<Cell> findPath(Maze currentMaze,Cell current,ArrayList<Cell> path, ArrayList<Cell> visited)
	{	
		ArrayList<Cell>options=currentMaze.getNeighbors(current);
		if(options.contains(currentMaze.getEndCell()))
		{
			path.add(current);
			path.add(options.get(options.lastIndexOf(currentMaze.getEndCell())));
			return path;
		}
		else if(options.contains(currentMaze.getStartCell()))
		{
			options.remove(currentMaze.getStartCell());
		}
	
			//Determine if any of the options have been visited yet
			boolean containsVisited=false;
			for(Cell option:options)
			{
				if(visited.contains(option))
					containsVisited=true;
			}
			//if all new spots go to the first one and add it to path
			if(containsVisited==false)
			{
				path.add(current);
				visited.add(current);
				current=options.get(0);
				return findPath(currentMaze,current,path,visited);
			}
			//if at least on of the options has been visited proceed here
			else if(containsVisited==true)
			{
					//check if any of the options are new spots
					boolean containsNew=false;
					for(int i=0;i<options.size();i++)
					{
						if(!visited.contains(options.get(i)))
						{
							containsNew=true;
						}
							
					}
					//if one of the options is a new spot find the first new spot
					//in options and go to it and add it to path
					if(containsNew==true)
					{
						ArrayList<Cell>toRemove=new ArrayList<Cell>();
						//remove all the visited cells to leave only the new cells
						for(Cell c:options)
						{
							if(visited.contains(c))
								toRemove.add(c);
						}
						for(Cell c:toRemove)
						{
							options.remove(c);
						}
						
						path.add(current);
						visited.add(current);
						current=options.get(0);
						return findPath(currentMaze,current,path,visited);
					}
					
					
					//if all of the options have been visited
					else if(containsNew==false)
					{
						
						
						//reverse the list of visited so chronological ordering
						//can be used to determine which cell was visited
						//most long ago
						ArrayList<Cell> reversedVisited=new ArrayList<Cell>();
						for(int i=visited.size()-1;i>=0;i--)
						{
							reversedVisited.add(visited.get(i));
						}
						//Leave only the option visited most long ago
						for(Cell beenTo:reversedVisited)
						{
							if(options.contains(beenTo)&&options.size()>1)
							{
								options.remove(beenTo);
							}
						}
						
						visited.add(current);
						path.remove(current);
						current=options.get(0);
						return findPath(currentMaze,current,path,visited);
					}
				}
			
		return path;
	}
	/**
	 * This method prints out a maze using the given solution path but also 
	 * accounts for the re-sizing of the display of the maze so '.' need to be 
	 * added to the gaps that are created by the re-sizing.
	 * @param m is the maze to be printed
	 * @param solution is the solution path for the maze
	 */
	public static  void printSolvedMaze(Maze m, ArrayList<Cell> solution)
	{
		char[][]theMaze=m.getMazeDisplay();
		//fill in all the correct spots in the solution
		for(Cell c:solution)
		{
			theMaze[c.getDisplayCol()][c.getDisplayRow()]='.';			
		}
		//fill in all of the correct spots that did not get filled in because of the re-sizing
		for(int i=0;i<solution.size();i++)
		{	//this ensures no out of bounds problems by going only until solution.size()-2
			if(i<=solution.size()-2)
			{
				//difference between the rows and the  columns of 2 consecutive solution cells is calculated
				//the appropriate spot bewtten these 2 cells is filled in based on which open spot due to the
				//re-sizing was not filled in.
				int rowDiff=solution.get(i+1).getDisplayRow()-solution.get(i).getDisplayRow();
				int colDiff=solution.get(i+1).getDisplayCol()-solution.get(i).getDisplayCol();
				if(rowDiff>0)
				{
					theMaze[solution.get(i).getDisplayCol()][solution.get(i).getDisplayRow()+1]='.';
				}
				else if(rowDiff<0)
				{
					theMaze[solution.get(i).getDisplayCol()][solution.get(i).getDisplayRow()-1]='.';
				}
				else if(colDiff>0)
				{
					theMaze[solution.get(i).getDisplayCol()+1][solution.get(i).getDisplayRow()]='.';
				}
				else if(colDiff<0)
				{
					theMaze[solution.get(i).getDisplayCol()-1][solution.get(i).getDisplayRow()]='.';
				}
			}
		}
		System.out.println("");
		System.out.println("Solution: ");
		m.printMaze(theMaze);
	}
	/**
	 * This method uses command line args to set the ID of the maze. By default the maze goes to 20x6.
	 * If the command line args do not specify an ID, then ID is set to 1. If more than 1 command line 
	 * arg is given, an error message is printed and the program terminates. If the program did not need
	 * to be terminated, first the unsolved maze is printed, then a call to findPath() is made to find 
	 * the correct solution of the maze, which is then passes to printSolvedMaze to print the maze with 
	 * the solution onto the screen.
	 * @param args the ID of the maze should be in the first index of args
	 */
	public static void main (String[] args)
	{
		Maze m = new Maze(20,6,1);
		if(args.length>0&&args.length<2)
		{
			 m = new Maze(20,6, Integer.parseInt(args[0]));
		}
		else if(args.length==0)
		{
			 m = new Maze(20,6, 1);
		}
		else
		{
			System.out.println("Invalid number of command line arguments,");
			System.exit(0);
		}
		m.printMaze();
		ArrayList<Cell> solvedMaze=findPath(m);
		printSolvedMaze(m,solvedMaze);
	}
}
