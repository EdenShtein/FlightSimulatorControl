package server_side;

public interface Server {
	
	public void open(ClientHandler c, String exitStr) throws Exception; // open the server via port
	public void stop(); // stops the servers completely
	public void start(ClientHandler ch, String exitStr); // starting the server
}
