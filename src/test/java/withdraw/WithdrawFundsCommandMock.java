package withdraw;

import java.io.EOFException;
import java.io.IOException;
import java.net.UnknownHostException;

import command.client.BankClientCommand;
import command.client.ClientCommand;
import command.client.ClientConnection;

public class WithdrawFundsCommandMock extends ClientCommand implements BankClientCommand  {

	
	
	public WithdrawFundsCommandMock(ClientConnection connection) {
		super(connection);
	}

	@Override
	public void execute() throws UnknownHostException, IOException, ClassNotFoundException {
		
	    String message = null;
	  
	    sendMessageInteger(new Integer(1));
		
		System.out.println("Choose active account - insert"+
				 "number");
		
		sendMessageInteger(new Integer(66));
		try{
		message = getMessageString();
		}
		catch(EOFException e){
			
		}
		System.out.println("Message " + message);
		
		
		sendMessageFloat(1f);
		message = getMessageString();
		
	}

	public Object getMessageObject() throws ClassNotFoundException, IOException{
		return connection.getIn().readObject();
	}
	
	void sendMessageFloat(final Float msg) throws IOException {
		
		connection.getOut().writeObject(msg);
		connection.getOut().flush();
		
	}
	
}
