package server_side;

import java.io.BufferedReader;   
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import algorithms.MatrixProblem;
import algorithms.Position;
import algorithms.State;
//Searchable Client Handler is a special type of client handler that deals with the matrix problem
public class SearchableClientHandler<Solution, StateType> implements ClientHandler<MatrixProblem, Solution> {
	private Solver solver;
	private CacheManager cm;
	
	public SearchableClientHandler(Solver<MatrixProblem, Solution> solver, CacheManager<MatrixProblem, Solution> cm) {
		this.solver = solver;
		this.cm = cm;
	}
	
	@Override
	public void handleClient(InputStream in, OutputStream out, String exitStr) throws Exception {
		// Read from the user
		BufferedReader userInput= new BufferedReader(new InputStreamReader(in));
		// Write to the user
		PrintWriter userOutput = new PrintWriter(out);
		String line;
		// Initial the matrix that will be given from the client
		ArrayList<int[]> getMatrix = new ArrayList<>();
		while(!(line=userInput.readLine()).equals(exitStr))
		{
			getMatrix.add(Arrays.asList(line.split(",")).stream().mapToInt(Integer::parseInt).toArray()); //Stream like in Lecture 8 
			
		}
			Position start, goal; //the user gives us a start position and goal position in the matrix
			String[] tmpSplit;
			line = userInput.readLine(); // reading the initial start position and convert to integer
			tmpSplit = line.split(",");
			start = new Position(Integer.parseInt(tmpSplit[0]), Integer.parseInt(tmpSplit[1]));
			
			line = userInput.readLine(); // reading the goal position and convert to integer
			tmpSplit = line.split(",");
			goal = new Position(Integer.parseInt(tmpSplit[0]), Integer.parseInt(tmpSplit[1]));
			
			// Convert ArrayList to int[][]
			
			int[][] finalMatrix = new int[getMatrix.size()][];
			Iterator<int[]> it = getMatrix.iterator();
			
			int i = 0;
			int matrixWidth = 0;
			int[] tmp;
			
			while(it.hasNext())
			{
				tmp = it.next();
				matrixWidth = tmp.length;
				finalMatrix[i] = new int[tmp.length]; 
				System.arraycopy(tmp, 0, finalMatrix[i], 0, tmp.length);
				
				i++;
			}
			
			ArrayList<Position> goalList = new ArrayList<>();
			goalList.add(goal);
			
			MatrixProblem maze = new MatrixProblem(finalMatrix, matrixWidth, getMatrix.size(), start, goalList);
			
			if(cm.isSolutionCached(maze) == true) //if the solution is already solved 
			{
				userOutput.println(cm.getSolution(maze));
			}
			else
			{
				// Getting the solution
				List<State<Position>> pathList = (List<State<Position>>) solver.solve(maze); // In run time it will be SearcerSolver that run the method solve.
				String[] DirectionList = maze.getDirection(pathList);
				Solution s = (Solution) String.join(",", DirectionList); //method concatenates the given elements with the delimiter and returns the concatenated string. 
				
				// Saving it
				cm.saveSolution(maze, s);
				
				// Printing it back to the client
				userOutput.println(s);
			}
			
			userOutput.flush();
		
		}
		
	
}





