package command.bank;

import dao.AccountDAOImpl;
import dao.ClientDAOImpl;
import dao.DAOException;
import exception.BankNotFoundException;


public class DBRemoveClientCommander implements Command {
	Integer id;

	public void execute() throws DAOException, BankNotFoundException {
		
		ClientDAOImpl cdaoI = new ClientDAOImpl();
		AccountDAOImpl acc = new AccountDAOImpl();
		acc.removeByClientId(BankCommander.getCurrentClient().getId());
		cdaoI.remove(BankCommander.getCurrentClient());
	}

	public DBRemoveClientCommander setId(Integer id){
		this.id = id;
		return this;
	}
	
	public void printCommandInfo() {
		System.out.println("Remove client");
		
	}

}
