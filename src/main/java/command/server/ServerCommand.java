package command.server;

import java.io.IOException;

import command.bank.BankCommander;
import command.bank.DBSelectBankCommander;
import command.bank.DBSelectClientCommander;
import dao.DAOException;
import exception.BankNotFoundException;

public abstract class ServerCommand implements BankServerCommand{
	protected  ServerConnection connection;
	
	public ServerCommand(ServerConnection serverConnection){
		connection = serverConnection;
	}
	
	public ServerConnection getServerconnection(){
		return connection;
	}
	
	 protected void sendMessageString(String msg) throws IOException {
		connection.getOut().writeObject(msg);
		connection.getOut().flush();
		
	}
	 
	 String getMessageString() throws ClassNotFoundException, IOException{
			return (String) connection.getIn().readObject();
	}
	 
	 protected void updateBankCommander() throws NumberFormatException, DAOException, IOException, BankNotFoundException{
			String bankName = BankCommander.getCurrentBank().getName();
			((DBSelectBankCommander)BankCommander.getMap().get("SetActiveBank")).setBankName(bankName).execute();
			String clientName = BankCommander.getCurrentClient().getName();
			((DBSelectClientCommander)BankCommander.getMap().get("SetActiveClient")).setClientName(clientName).execute();
		}
}
