package command.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection {
	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;
	public ServerConnection(Socket socket) throws IOException{
		this.socket = socket;
		in = new ObjectInputStream(socket.getInputStream());	
		out = new ObjectOutputStream(socket.getOutputStream());
		
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public ObjectOutputStream getOut() {
		return out;
	}

	public ObjectInputStream getIn() {
		return in;
	}
	
}
