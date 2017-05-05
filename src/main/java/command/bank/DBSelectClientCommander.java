package command.bank;

import java.io.IOException;
import dao.ClientDAOImpl;
import dao.DAOException;
import exception.BankNotFoundException;

public class DBSelectClientCommander implements Command {
	private String clientName;
	
	private void selectClient(String clientName) throws DAOException, BankNotFoundException{
		
		ClientDAOImpl cdaoI = new ClientDAOImpl();
		BankCommander.setCurrentClient(cdaoI.findClientByName(BankCommander.getCurrentBank(), clientName));
	}

	
	public void execute() throws DAOException, BankNotFoundException, IOException  {
		
		selectClient(clientName);
				
	}

	
	public void printCommandInfo() {
		System.out.println("Set Active Client");
		
	}
	
	public DBSelectClientCommander setClientName(String clientName){
		this.clientName = clientName;
		return this;
	}

}
