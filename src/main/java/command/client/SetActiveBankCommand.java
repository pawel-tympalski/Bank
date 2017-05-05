package command.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class SetActiveBankCommand extends ClientCommand  {
	
	public SetActiveBankCommand(ClientConnection connection) {
		super(connection);
		
	}

	@Override
	public void execute() throws UnknownHostException, IOException, ClassNotFoundException {
		
	    String message = null;
	    InputStreamReader isp = new InputStreamReader(System.in);
		BufferedReader inbr = new BufferedReader(isp);
	    
	    sendMessageInteger(1);
	    message = getMessageString();
	    System.out.println(message);
	    message = inbr.readLine();
	    sendMessageString(message);
	    message = getMessageString();
	    if(message.equals("The Bank of given name does not exist in database")){
			System.out.println(message);	
		} 
	    else{
	    	System.out.println("Bank of given name is set up correctly");
	    }
	}

	
}
