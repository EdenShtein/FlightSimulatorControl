package test;

import java.util.Random;

public class MainTrain {

	public static void main(String[] args) {
		//----------- ex1 --------------
//		DesignTest dt=new DesignTest();
//		TestSetter.setClasses(dt);
//		dt.testDesign();
		
		//----------- ex2 --------------
		// execution test (40 points)
		Random r=new Random();
		int port=6000+r.nextInt(1000);
		TestSetter.runServer(port);
		try{
			System.out.println("test client 1");
			TestServer.runClient(port);
			System.out.println("test client 2");
			TestServer.runClient(port);
			System.out.println("test client 3");
			TestServer.runClient(port);
			System.out.println("test client 4");
			TestServer.runClient(port);
			System.out.println("test client 5");
			TestServer.runClient(port);
		}finally{
			TestSetter.stopServer();
		}
		
		
		System.out.println("done");
	}

}
