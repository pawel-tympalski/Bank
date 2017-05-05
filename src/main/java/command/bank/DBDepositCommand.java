package command.bank;

import java.io.IOException;
import dao.AccountDAOImpl;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;

public class DBDepositCommand implements Command {
	private float funds;
	@Override
	public void execute() throws NumberFormatException, IOException, DAOException, BankNotFoundException, BankException {

		BankCommander.getCurrentClient().getActiveAccount().deposit(funds);
		new AccountDAOImpl().save(BankCommander.getCurrentClient().getActiveAccount());
		
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Deposit funds");

	}
	
	public DBDepositCommand setFunds(float funds){
		this.funds = funds;
		return this;
	}

}
