package command.server;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import command.bank.BankCommander;
import command.bank.DBWithdrawCommand;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;
import model.AbstractAccount;

public class WithdrawServerCommand extends ServerCommand  {
	private ReentrantLock lock;
	public  WithdrawServerCommand(ServerConnection serverConnection) {
		super(serverConnection);
	}
	
	public WithdrawServerCommand(ServerConnection serverConnection, ReentrantLock lock) {
		super(serverConnection);
		this.lock = lock;
	}

	@Override
	public void execute() throws IOException, ClassNotFoundException, DAOException, BankNotFoundException, NumberFormatException, BankException {
		
		
		Set<AbstractAccount> accounts = BankCommander.getCurrentClient().getAccounts();
		
		connection.getOut().writeObject(accounts);
		connection.getOut().flush();
		
		Integer number = (Integer)connection.getIn().readObject();
		System.out.println("The account number is " + number);
		setActiveAccount(accounts, number);
		
		sendMessageString("Choose money to withdraw");
		Float funds = (Float)connection.getIn().readObject();
		lock.lock();
		try{
			((DBWithdrawCommand)BankCommander.getMap().get("Withdraw")).setFunds(funds).execute();
			updateBankCommander();
		}
		finally{
			lock.unlock();
		}
		sendMessageString("Money has been withdrawn");
		
	}

	public void setActiveAccount(Set<AbstractAccount> accounts, Integer number) {
		for(AbstractAccount a : accounts){
			if(a.getId() == number){
				BankCommander.getCurrentClient().setActiveAccount(a);
				break;
			}
		}
	}
}
