package command.bank;

import java.io.IOException;
import java.util.Set;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;
import model.AbstractAccount;

public class SetActiveAccount implements Command {
	int number;
	@Override
	public void execute()
			throws DAOException, IOException, NumberFormatException, BankNotFoundException, BankException {
		
		Set<AbstractAccount> accounts = BankCommander.getCurrentClient().getAccounts();
		for(AbstractAccount account : accounts ){
			if( account.getId() == number){
				BankCommander.getCurrentClient().setActiveAccount(account);
				break;
			}
		}

	}

	public SetActiveAccount setNumber(int number){
		this.number = number;
		return this;
	}
	
	@Override
	public void printCommandInfo() {
		
	}

}
