package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.InflaterInputStream;

public class MyTestClientHandler <Problem,Solution> implements ClientHandler <Problem,Solution> {
	private Solver solver;
	private CacheManager cm;
	
	
	public MyTestClientHandler(Solver solver, CacheManager cm) {
		this.solver = solver;
		this.cm = cm;
	}
	
	@Override
	public void handleClient(InputStream in, OutputStream out, String exitStr) throws Exception
	{
		// Read from the user
		BufferedReader userInput= new BufferedReader(new InputStreamReader(in));
		// Write to the user
		PrintWriter userOutput = new PrintWriter(out);
		String line;
		while(!(line=userInput.readLine()).equals(exitStr))
		{
			//Check if the problem is already solved and there is a cache
			if(cm.isSolutionCached(line)==true)
			{
				userOutput.println(cm.getSolution(line));
				
			}
			else
			{
				// Get the solution
				Solution s = (Solution) solver.solve(line);
				
				//saving the solution
				cm.saveSolution(line, s);
				
				//Printing it to the user 
				userOutput.println(s);
			}
			userOutput.flush();
		}
		
	}

}
