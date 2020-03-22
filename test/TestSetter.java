package test;

import algorithms.BestFirstSearch;
import algorithms.MatrixProblem;
import algorithms.Position;
import server_side.FileCacheManager;
import server_side.MySerialServer;
import server_side.SearchableClientHandler;
import server_side.SearcherSolver;
import server_side.Server;



public class TestSetter {
	

	static Server s; 
	
	public static void runServer(int port) {
		s = new MySerialServer(port);
		try {
			FileCacheManager<MatrixProblem, String> cm=new FileCacheManager<MatrixProblem, String>();
			SearcherSolver<MatrixProblem, String, Position> solver =new SearcherSolver<MatrixProblem, String, Position>(new BestFirstSearch<Position>());
			SearchableClientHandler<String, Position>  ch =new SearchableClientHandler<>(solver, cm);	
			System.out.println("The server is strating now:");
			s.start(ch, "end");
		}catch(Exception e) {e.printStackTrace();}
	}

	public static void stopServer() {
		s.stop();
	}
	

}
