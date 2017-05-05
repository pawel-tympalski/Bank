package model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class BankInfo implements Serializable{
	
	private static final long serialVersionUID = 6472878350265111167L;
	private int numberOfClients;
	private int numberOfAccounts;
	private float overallBalance;
	private Bank bank;
	
	public void setBank(Bank bank){
		this.bank = bank;
	}
	
	public void numberOfClients(){
		  
		  numberOfClients = bank.getClients().size();
	}
	
	public void numberOfAccounts(){
		  
		  int numberOfAccounts = 0;
		  int number = 0;
		  List<Client> clients = bank.getClients();
		  for(Client c : clients){
			  number = c.getAccounts().size();
			  numberOfAccounts += number;
		  }
		  this.numberOfAccounts = numberOfAccounts;	  
	  }
	
	
	public void overalBalance(){
  		float value = 0;
		List<Client> clients = bank.getClients();
		for(Client c : clients){
			Set<AbstractAccount> accounts = c.getAccounts();
			for(AbstractAccount a : accounts){
				value += a.getBalance();
			}
		}
		overallBalance = value;
  	}

	public String toString(){
		numberOfClients();
		numberOfAccounts();
		overalBalance();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Bank Statistics\n");
		sb.append("Number of Clients: " + numberOfClients +"\n");
		sb.append("Total amount of credits granted to the bank clients: " + overallBalance);
		sb.append("\nNumber of Accounts: " + numberOfAccounts);
		
		return sb.toString();	
	}

}
