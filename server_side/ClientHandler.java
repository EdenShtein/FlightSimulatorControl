package server_side;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler<Problem, Solution> {
	public void handleClient(InputStream in, OutputStream out, String exitStr) throws Exception;
}
