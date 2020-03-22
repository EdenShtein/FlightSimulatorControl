package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Random;

public class TestServer {
	
	
	public static void runClient(int port){
		Socket s=null;
		PrintWriter out=null;
		BufferedReader in=null;
		try{
			s=new Socket("127.0.0.1",port);
			s.setSoTimeout(3000);
			out=new PrintWriter(s.getOutputStream());
			in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			Random r=new Random() ;
			int[][] matrix=new int[4][4];
			for(int i=0;i<matrix.length;i++)
				for(int j=0;j<matrix[i].length;j++)
					matrix[i][j]=100+r.nextInt(101);

			StringBuilder sol=new StringBuilder();
			int i=0,j=0;
			while(i<3 || j<3){
				if(j<3 && r.nextBoolean()){
					sol.append(",Right");
					j++;
					matrix[i][j]=r.nextInt(100);
				}else{
					if(i<3){
						sol.append(",Down");
						i++;
						matrix[i][j]=r.nextInt(100);						
					}
				}
			}
			String fsol=sol.substring(1);			 
			System.out.println("\tsending problem...");
			for(i=0;i<matrix.length;i++){
				System.out.print("\t");
				for(j=0;j<matrix[i].length-1;j++){
					out.print(matrix[i][j]+",");
					System.out.print(matrix[i][j]+",");
				}
				out.println(matrix[i][j]);
				System.out.println(matrix[i][j]);
			}
			out.println("end");
			out.println("0,0");
			out.println("3,3");
			out.flush();
			System.out.println("\tend\n\t0,0\n\t3,3");
			System.out.println("\tproblem sent, waiting for solution...");
			String usol=in.readLine();
			System.out.println("\tsolution received");
			if(!usol.equals(fsol)){
				System.out.println("\twrong answer from your server (-20)");
			
				System.out.println("\t\tyour solution: "+usol);
				System.out.println("\t\texpected solution: "+fsol);
			}
			
		}catch(SocketTimeoutException e){
			System.out.println("\tYour Server takes over 3 seconds to answer (-20)");
		}catch(IOException e){
			System.out.println("\tYour Server ran into some IOException (-20)");
		}finally{
			try {
				in.close();
				out.close();
				s.close();
			} catch (IOException e) {
				System.out.println("\tYour Server ran into some IOException (-20)");
			}
		}
		
	}

}
