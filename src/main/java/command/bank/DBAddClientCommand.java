package command.bank;

import dao.ClientDAOImpl;
import dao.DAOException;
import model.Client;


public class DBAddClientCommand implements Command {

	Client client;
	
	public void execute() throws DAOException  {
		
		ClientDAOImpl clientDAOImpl = new ClientDAOImpl();
		clientDAOImpl.save(client);
		System.out.println("Client has been added");
		   
	}
	public DBAddClientCommand setClient(Client client){
		this.client = client;
		return this;
	}
	
	public void printCommandInfo() {
		System.out.println("Add client");

	}

}
