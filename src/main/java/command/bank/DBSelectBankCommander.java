package command.bank;

import java.io.IOException;
import java.util.List;
import dao.ClientDAOImpl;
import dao.DAOException;
import exception.BankNotFoundException;
import model.Bank;
import model.Client;

public class DBSelectBankCommander implements Command {
	private String bankName;

	public void selectBank() throws DAOException, BankNotFoundException, IOException {
		
		ClientDAOImpl cdaoi = new ClientDAOImpl();
		List<Client> clientList = cdaoi.getAllClients(bankName);
		Bank bank = new Bank(bankName);
		bank.setClients(clientList);

		BankCommander.setCurrentBank(bank);
		System.out.println(BankCommander.getCurrentBank().getName());
	}

	@Override
	public void execute() throws DAOException, IOException, NumberFormatException, BankNotFoundException {
		selectBank();
		
	}

	@Override
	public void printCommandInfo() {
		
		
	}
	
	public DBSelectBankCommander setBankName(String bankName){
		this.bankName = bankName;
		return this;
	}
	
}
