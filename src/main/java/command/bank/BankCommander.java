package command.bank;

import java.util.Map;
import java.util.TreeMap;
import model.Bank;
import model.Client;

public class BankCommander {
	
	public static Bank currentBank ;
	public static Client currentClient;
	public static TreeMap<String, Command> map = new TreeMap<String,Command>();
	static{
		map.put("SetActiveBank",new DBSelectBankCommander() );
		map.put("SetActiveClient", new DBSelectClientCommander());
		map.put("Withdraw", new DBWithdrawCommand());
		map.put("Deposit", new DBDepositCommand());
		map.put("BankStatistics", new BankStatisticsCommand());
		map.put("AddClient", new DBAddClientCommand());
		map.put("RemoveClient", new DBRemoveClientCommander());
		map.put("SetActiveAccount", new SetActiveAccount());
	}
	
		
	public static void removeCommand(String name){
		
		String key;
		Command value;
		
		for(Map.Entry<String,Command> entry : BankCommander.map.entrySet()) {
			
			key = entry.getKey();
			value = entry.getValue();
			
			if(entry.getKey().equals(name)){
				
				BankCommander.map.remove(key, value);
				
			}
		}	
		
	}
	
	public static void registerCommand(String name, Command command){
		
		BankCommander.map.put(name, command);
		
	}
	

	public static Bank getCurrentBank() {
		return currentBank;
	}


	public static void setCurrentBank(Bank currentBank) {
		BankCommander.currentBank = currentBank;
	}


	public static Client getCurrentClient() {
		return currentClient;
	}


	public static void setCurrentClient(Client currentClient) {
		BankCommander.currentClient = currentClient;
	}


	public static TreeMap<String, Command> getMap() {
		return map;
	}


	public static void setMap(TreeMap<String, Command> map) {
		BankCommander.map = map;
	}
}
