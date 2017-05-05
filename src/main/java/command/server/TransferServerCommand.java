package command.server;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import command.bank.BankCommander;
import command.bank.DBDepositCommand;
import command.bank.DBSelectClientCommander;
import command.bank.DBWithdrawCommand;
import command.bank.SetActiveAccount;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;
import model.AbstractAccount;

public class TransferServerCommand extends ServerCommand {
	private ReentrantLock lock;
	public TransferServerCommand(ServerConnection serverConnection) {
		super(serverConnection);
		
	}

	public TransferServerCommand(ServerConnection serverConnection, ReentrantLock lock) {
		super(serverConnection);
		this.lock = lock;
	}

	@Override
	public void execute() throws IOException, ClassNotFoundException, DAOException, BankNotFoundException, NumberFormatException, BankException {
		
		String clientName;
		Set<AbstractAccount> accounts = BankCommander.getCurrentClient().getAccounts();
		connection.getOut().writeObject(accounts);
		Integer number = (Integer)connection.getIn().readObject();
		setActiveAccount(accounts, number);
		
		sendMessageString("Choose money to trnasfer");
		Float funds = (Float)connection.getIn().readObject();
		lock.lock();
		try{
			((DBWithdrawCommand)BankCommander.getMap().get("Withdraw")).setFunds(funds).execute();
			sendMessageString("Choose name of the client who will receive the money");
			clientName = (String)connection.getIn().readObject();
			
			String name = BankCommander.getCurrentClient().getName();
			((DBSelectClientCommander)BankCommander.getMap().get("SetActiveClient")).setClientName(clientName).execute();
			accounts = BankCommander.getCurrentClient().getAccounts();
			connection.getOut().writeObject(accounts);
			number = (Integer)connection.getIn().readObject();
			((SetActiveAccount)BankCommander.getMap().get("SetActiveAccount")).setNumber(number).execute(); 
			 
			((DBDepositCommand)BankCommander.getMap().get("Deposit")).setFunds(funds).execute();
			((DBSelectClientCommander)BankCommander.getMap().get("SetActiveClient")).setClientName(name).execute();
			updateBankCommander();
			
			sendMessageString("Money has been transfered");
		}
		finally{
			lock.unlock();
		}	
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
