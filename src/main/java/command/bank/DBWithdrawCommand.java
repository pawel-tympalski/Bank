package command.bank;

import java.io.IOException;
import dao.AccountDAOImpl;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;

public class DBWithdrawCommand implements Command {

	private float funds;
	@Override
	public  void execute()
			throws DAOException, IOException, NumberFormatException, BankNotFoundException, BankException {
		
		BankCommander.getCurrentClient().getActiveAccount().withdraw(funds);
		new AccountDAOImpl().save(BankCommander.getCurrentClient().getActiveAccount());
	}

	@Override
	public void printCommandInfo() {
		
	}
	
	public DBWithdrawCommand setFunds(float funds){
		this.funds = funds;
		return this;
	}

}
