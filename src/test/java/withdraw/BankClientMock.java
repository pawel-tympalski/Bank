package withdraw;


import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import command.client.BankClientCommand;

import command.client.ClientConnection;
import command.client.DepositFundsCommand;
import command.client.ExitClientCommand;
import command.client.TransferFunds;

public class BankClientMock implements Runnable {
	
	static final String SERVER = "localhost";
	Map<Integer,BankClientCommand> map;
	
	@Override
	public void run() {
		
		
		ClientConnection connection = null;
			try{
				 
				connection= new ClientConnection(SERVER, 4000);
				
				generateBankClientCommandMap(connection);
				
				BankClientCommand clientCommand = map.get(1);
				
				clientCommand.execute();
				
			} catch (ClassNotFoundException | IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
			}
			finally{
				try {
					connection.getRequestSocket().close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
				
		}			
	
     
	
     private  void generateBankClientCommandMap(ClientConnection connection){
    	 Map<Integer,BankClientCommand> map = new HashMap<Integer,BankClientCommand>();
    	 map.put(1, new WithdrawFundsCommandMock(connection));
    	 map.put(2, new DepositFundsCommand(connection));
    	 map.put(3, new TransferFunds(connection));
    	 map.put(4, new ExitClientCommand(connection));
    	 
    	 this.map = map;
     }
}
