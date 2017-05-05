package command.server;

import java.io.IOException;
import dao.DAOException;
import exception.BankNotFoundException;


public class ExitServerCommand extends ServerCommand{
	
	public ExitServerCommand(ServerConnection serverConnection) {
		super(serverConnection);
		
	}

	@Override
	public void execute() throws IOException, ClassNotFoundException, DAOException, BankNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
	}

	
}
