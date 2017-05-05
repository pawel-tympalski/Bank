package command.bank;

import dao.ClientDAOImpl;
import dao.DAOException;
import exception.BankNotFoundException;
import model.Client;

public class DBFindClientCommand implements Command {
	private String nameOfClient;
	@Override
	public void execute() throws DAOException, BankNotFoundException {
		
		ClientDAOImpl cdao = new ClientDAOImpl();
		Client client =  cdao.findClientByName(BankCommander.getCurrentBank(), nameOfClient);
		BankCommander.setCurrentClient(client);
		
	}
	
	public DBFindClientCommand setNameOfClient(String nameOfClient){
		this.nameOfClient = nameOfClient;
		return this;
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Find client");

	}

}
