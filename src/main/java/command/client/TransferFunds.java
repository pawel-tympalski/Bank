package command.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Set;
import model.AbstractAccount;

public class TransferFunds extends ClientCommand {

	
	public TransferFunds(ClientConnection connection) {
		super(connection);
	}

	@Override
	public void execute() throws UnknownHostException, IOException, ClassNotFoundException {
		
		String message = null;
	    InputStreamReader isp = new InputStreamReader(System.in);
		BufferedReader inbr = new BufferedReader(isp);
		
		sendMessageInteger(3);
		@SuppressWarnings("unchecked")
		Set<AbstractAccount> accountsList = (Set<AbstractAccount>)getMessageObject();
		System.out.println("List of Accounts :");
		for (AbstractAccount a : accountsList) {
			System.out.println("Nr " + a.getId() + " " + "Balance "+
			a.getAccountType() + " " +
			a.getBalance() );
		}
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
		String name = inbr.readLine();
		sendMessageString(name);
		
		accountsList =  (Set<AbstractAccount>)getMessageObject();
		System.out.println("List of Accounts :");
		for (AbstractAccount a : accountsList) {
			System.out.println("Nr " + a.getId() + " " + "Balance "+
					a.getAccountType() + " " +
					a.getBalance() );
			
		}
		System.out.println("Choose active account - insert"+
				 "number");
		accountNumber = Integer.parseInt(inbr.readLine());
		sendMessageInteger(accountNumber);
		
		message = getMessageString();
		
		System.out.println(message);
		
	}
	
	public Object getMessageObject() throws ClassNotFoundException, IOException{
		return connection.getIn().readObject();
	}
	void sendMessageFloat(final Float msg) throws IOException {
		
		connection.getOut().writeObject(msg);
		connection.getOut().flush();
	}
}
