package command.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class SetActiveClientCommand extends ClientCommand  {
	
	
	public SetActiveClientCommand(ClientConnection connection) {
		super(connection);
		
	}

	@Override
	public void execute() throws UnknownHostException, IOException, ClassNotFoundException {
		
	    String message = null;
	    InputStreamReader isp = new InputStreamReader(System.in);
		BufferedReader inbr = new BufferedReader(isp);	
		
		sendMessageInteger(2);
		message = getMessageString();
		System.out.println(message);
		message = inbr.readLine();
		sendMessageString(message);
		message = getMessageString();
		if(message.equals("Client of given name does not exist")){
			System.out.println(message);	
		}
		else{
			System.out.println("Client of given name set properly");
		}
	}

}
