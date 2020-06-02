package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MyTestClientHandler<Problem,Solution> implements ClientHandler {

	@SuppressWarnings("rawtypes")
	private Solver solver;
	@SuppressWarnings("rawtypes")
	private CacheManager cm;
	
	public MyTestClientHandler(CacheManager cm ,Solver s) {
		this.solver=s;
		this.cm=cm;
	}

	
	@Override
	public void handleClient(InputStream in, OutputStream out)  {
		BufferedReader Bin=new BufferedReader(new InputStreamReader(in));
		PrintWriter Bout=new PrintWriter(new OutputStreamWriter(out));
		try {
			Problem Line;
			Solution Solved;
			
			while(!(Line=(Problem) Bin.readLine()).equals("end"))
			{
				
				if(cm.Check(Line))
				{
					Solved=(Solution)cm.Extract(Line);
				}
				else {
				//solver=String->new StringBuilder().reverse().toString();
				
				Solved=(Solution) solver.Solve(Line);
				cm.Save(Line, Solved);
				}
				Bout.println(Solved);
				Bout.flush();

			}
			
		}catch (IOException e) {e.printStackTrace();}
		
		try {
			Bin.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bout.close();
	}

}
