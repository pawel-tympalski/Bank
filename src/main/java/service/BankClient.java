package service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import command.client.BankClientCommand;
import command.client.ClientConnection;
import command.client.DepositFundsCommand;
import command.client.ExitClientCommand;
import command.client.TransferFunds;
import command.client.WithdrawFundsCommand;

public class BankClient implements Runnable{
	
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
				if(choice == 4){
					break;
				}
				
				} catch (ClassNotFoundException | IOException |  NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException  e) {
					
				e.printStackTrace();
			}
				
			}
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		finally{
			try {
				connection.closeConnection();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

	public void showMenu() {
		
		System.out.println(
		"Withdraw funds - press 1\n"+
		 "Deposit funds - press 2\n"+
		 "Transfer funds - press 3\n"+
		 "Exit - press 4\n"
		 + "Insert: ");
	}

	

     public static void main(final String args[]) {
           BankClient client = new BankClient();
           client.run();
     }
	
     private  void generateBankClientCommandMap(ClientConnection connection){
    	 Map<Integer,BankClientCommand> map = new HashMap<Integer,BankClientCommand>();
    	 map.put(1, new WithdrawFundsCommand(connection));
    	 map.put(2, new DepositFundsCommand(connection));
    	 map.put(3, new TransferFunds(connection));
    	 map.put(4, new ExitClientCommand(connection));
    	 
    	 this.map = map;
     } 
}
