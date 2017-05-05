package command.server;

import java.io.IOException;
import command.bank.BankCommander;
import command.bank.DBSelectClientCommander;
import dao.DAOException;
import exception.BankNotFoundException;

public class SetActiveClientServerCommand extends ServerCommand {
	
	public SetActiveClientServerCommand(ServerConnection serverConnection) {
		super(serverConnection);
		
	}

	@Override
	public void execute() throws IOException, ClassNotFoundException{
				
		sendMessageString("Please give name of the client");
		String message = getMessageString();
		try{
		((DBSelectClientCommander)BankCommander.getMap().get("SetActiveClient")).setClientName(message).execute(); 
		}
		catch(DAOException  | BankNotFoundException e){
		sendMessageString("Client of given name does not exist");
		}
			
	}
	

}
