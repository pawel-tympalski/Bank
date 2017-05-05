package command.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import dao.AccountDAOImpl;
import dao.DAOException;
import exception.BankNotFoundException;
import model.AbstractAccount;
import model.CheckingAccount;
import model.SavingAccount;

public class AddAccountCommand implements Command {

	
	public void execute() throws  NumberFormatException, IOException, DAOException, BankNotFoundException {

		InputStreamReader isp = new InputStreamReader(System.in);
		BufferedReader inbr = new BufferedReader(isp);
		float balance;
		AbstractAccount abstractAccount = null;

		System.out.println("Insert the balance");
		balance = Float.parseFloat(inbr.readLine());
		System.out.println("Choose account - SavingAccount type s / CheckingAccount type c");
		String type = inbr.readLine();
		if ("c".equalsIgnoreCase(type)) {

			abstractAccount = new CheckingAccount(balance, 1, BankCommander.getCurrentClient().getOverdraft());

		}
		if ("s".equalsIgnoreCase(type)) {

			abstractAccount = new SavingAccount(balance, 1);

		}

		AccountDAOImpl accountDAOImpl = new AccountDAOImpl();
		accountDAOImpl.add(abstractAccount);	

	}

	
	public void printCommandInfo() {
		System.out.println("Add account");
	}

}
