package command.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection {
	Socket	requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	public ClientConnection(String serverAddress, int portNumber) throws IOException{
		requestSocket = new Socket(serverAddress,portNumber);
		out = new ObjectOutputStream(requestSocket.getOutputStream());
		in = new ObjectInputStream(requestSocket.getInputStream());
	}
	
	
	
	public void closeConnection() throws IOException{
		requestSocket.close();
	}

	
	
	public Socket getRequestSocket() {
		return requestSocket;
	}



	public void setRequestSocket(Socket requestSocket) {
		this.requestSocket = requestSocket;
	}



	public ObjectOutputStream getOut() {
		return out;
	}



	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}



	public ObjectInputStream getIn() {
		return in;
	}



	public void setIn(ObjectInputStream in) {
		this.in = in;
	}



	void sendMessageInteger(final Integer msg, ObjectOutputStream out2) throws IOException {
		
		out.writeObject(msg);
		out.flush();
	}
	
	void sendMessageString(final String msg) throws IOException{
		out.writeObject(msg);
		out.flush();
	}

	String getMessageString(ObjectInputStream in) throws ClassNotFoundException, IOException{
		return (String) in.readObject();
	}
}
