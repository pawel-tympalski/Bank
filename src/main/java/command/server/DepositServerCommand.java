package command.server;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import command.bank.BankCommander;
import command.bank.DBDepositCommand;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;
import model.AbstractAccount;


public class DepositServerCommand extends ServerCommand   {
	ReentrantLock lock;
	public DepositServerCommand(ServerConnection serverConnection) {
		super(serverConnection);
		
	}

	public DepositServerCommand(ServerConnection serverConnection, ReentrantLock lock) {
		super(serverConnection);
		this.lock = lock;
	}

	@Override
	public void execute() throws IOException, ClassNotFoundException, DAOException, BankNotFoundException, NumberFormatException, BankException {
		
		Set<AbstractAccount> accounts = BankCommander.getCurrentClient().getAccounts();
		connection.getOut().writeObject(accounts);
		Integer number = (Integer)connection.getIn().readObject();
		setActiveAccount(accounts, number);
		
		sendMessageString("Choose money to deposit");
		Float funds = (Float)connection.getIn().readObject();
		lock.lock();
		try{
			((DBDepositCommand)BankCommander.getMap().get("Deposit")).setFunds(funds).execute();
			updateBankCommander();
		}
		finally{
			lock.unlock();
		}
		sendMessageString("Money has been deposited");
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
