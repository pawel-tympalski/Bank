package command.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Set;
import model.AbstractAccount;


public class DepositFundsCommand extends ClientCommand  {

	public DepositFundsCommand(ClientConnection connection) {
		super(connection);
	}


	@Override
	public void execute() throws UnknownHostException, IOException, ClassNotFoundException {
		
	    String message = null;
	    InputStreamReader isp = new InputStreamReader(System.in);
		BufferedReader inbr = new BufferedReader(isp);	

		sendMessageInteger(2);
	
		@SuppressWarnings("unchecked")
		Set<AbstractAccount> accounts =(Set<AbstractAccount>) getMessageObject();
		
		printClientAccountsDetails(accounts);
		System.out.println("Choose active account - insert"+
				 "number");
		Integer accountNumber = Integer.parseInt(inbr.readLine());
		sendMessageInteger(accountNumber);
		message = getMessageString();
		System.out.println(message);
		
		Float funds = Float.parseFloat(inbr.readLine());
		sendMessageFloat(funds);
		message = getMessageString();
		System.out.println(message);
	}


	public void printClientAccountsDetails(Set<AbstractAccount> accounts) {
		System.out.println("List of Accounts :");
		for(AbstractAccount a : accounts){
			System.out.println("Account Id " + a.getId() + " Balance " + a.getBalance() + " TypeAccount " + a.getAccountType());
		}
	}

	

	
	public Object getMessageObject( ) throws ClassNotFoundException, IOException{
		return connection.getIn().readObject();
	}

	
	void sendMessageFloat(final Float msg) throws IOException {
		
		connection.getOut().writeObject(msg);
		connection.getOut().flush();
	}
}
