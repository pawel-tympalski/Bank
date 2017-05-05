package command.server;

import java.io.IOException;
import command.bank.BankCommander;
import command.bank.DBSelectBankCommander;
import dao.DAOException;
import exception.BankNotFoundException;


public class SetAcitveBankServerCommand extends ServerCommand  {
	
	public SetAcitveBankServerCommand(ServerConnection serverConnection) {
		super(serverConnection);
		
	}

	@Override
	public void execute() throws IOException   {
		try{
			
			sendMessageString("Please give name of the bank");
			String message = getMessageString();
			((DBSelectBankCommander)BankCommander.getMap().get("SetActiveBank")).setBankName(message).execute();
				
		} catch (DAOException e1) {
			sendMessageString("The Bank of given name does not exist in database");
			e1.printStackTrace();
		} catch (BankNotFoundException e) {
			sendMessageString("The Bank of given name does not exist in database");
			
		}
		 catch (ClassNotFoundException e) {
			sendMessageString("The Bank of given name does not exist in database");
			e.printStackTrace();
		}
	}
}
