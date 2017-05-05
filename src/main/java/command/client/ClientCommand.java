package command.client;

import java.io.IOException;

public abstract class ClientCommand implements BankClientCommand {
	
	protected  ClientConnection connection;
	
	public ClientCommand(ClientConnection connection){
		this.connection = connection;
	}
	
	public void sendMessageInteger(final Integer msg) throws IOException {
		
		connection.getOut().writeObject(msg);
		connection.getOut().flush();
	
	
	}
	public void sendMessageString(final String msg) throws IOException{
	connection.getOut().writeObject(msg);
	connection.getOut().flush();
	}

	public String getMessageString() throws ClassNotFoundException, IOException{
	
		return (String) connection.getIn().readObject();
	}
	
	public ClientConnection getClientConnection(){
		return connection;
	}

}
