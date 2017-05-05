package service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import command.client.BankClientCommand;
import command.client.BankStatisticsClientCommand;
import command.client.ClientConnection;
import command.client.DepositFundsCommand;
import command.client.ExitClientCommand;
import command.client.TransferFunds;
import command.client.WithdrawFundsCommand;

public class BankRemoteOffice implements Runnable{
	
	static final String SERVER = "localhost";
	Map<Integer,BankClientCommand> map;
	InputStreamReader isp = new InputStreamReader(System.in);
	BufferedReader inbr = new BufferedReader(isp);
	
	@Override
	public void run() {
		
		ClientConnection connection = null;
		try {
			connection= new ClientConnection(SERVER, 4000);
			generateBankClientCommandMap(connection);
		
		while(true){
			try{
				showMenu();
				Integer choice = Integer.parseInt(inbr.readLine());
				BankClientCommand clientCommand = map.get(choice);
				clientCommand.execute();
				} catch (ClassNotFoundException | IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					
				e.printStackTrace();
			}
				
			}
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}

	public void showMenu() {
		
		System.out.println(
		"BankStatistics - press 30\n"+
		"Remove Client - press 40\n"+
		 "Add Client - press 50\n"+
		 "Exit - press 4\n"
		 + "Insert: ");
	}

	

     public static void main(final String args[]) {
           BankRemoteOffice client = new BankRemoteOffice();
           client.run();
     }
	
     private  void generateBankClientCommandMap(ClientConnection connection){
    	 Map<Integer,BankClientCommand> map = new HashMap<Integer,BankClientCommand>();
    	 map.put(1, new WithdrawFundsCommand(connection));
    	 map.put(2, new DepositFundsCommand(connection));
    	 map.put(3, new TransferFunds(connection));
    	 map.put(4, new ExitClientCommand(connection));
    	 map.put(30, new BankStatisticsClientCommand(connection));
    	 
    	 this.map = map;
     }
}
