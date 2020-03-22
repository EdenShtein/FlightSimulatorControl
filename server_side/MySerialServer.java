package server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server {
	
	private int port;
	private volatile boolean stop;
	public MySerialServer(int port)
	{
		this.port=port;
		this.stop=false;
	}
	
	@Override
	public void open(ClientHandler ch, String exitStr) throws Exception 
	{
		ServerSocket server=new ServerSocket(port);
		server.setSoTimeout(1000);
		while(!stop)
		{
			try {
				Socket aClient=server.accept();
				try {
					// The communication with the Client happens here:
					ch.handleClient(aClient.getInputStream(), aClient.getOutputStream(),exitStr);
					aClient.getInputStream().close();
					aClient.getOutputStream().close();
					aClient.close();
				}
				catch (IOException e) {}
			}
			catch (SocketTimeoutException e) {}
		}
		server.close();
	}
	@Override
	public void stop() 
	{
		stop=true;
	}
	@Override
	public void start(ClientHandler ch, String exitStr) 
	{
		new Thread( ()->
		{
			try
			{
				open(ch,exitStr);
			} catch (Exception e) {e.printStackTrace();}
		}).start();
	}

}
