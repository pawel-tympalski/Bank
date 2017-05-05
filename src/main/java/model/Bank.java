package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import listener.ClientRegistrationListener;
import service.Report;

public class Bank implements Report {
	
	List<Client> clients;
	List<ClientRegistrationListener> listeners;
	private String name;
	
	public Bank(){
		
		clients = new ArrayList<Client>();
		
		
		class PrintClientListener implements ClientRegistrationListener {

			@Override
			public void onClientAdded(Client c) {
				
				System.out.println("Client " + c.getName()+ " has been added");
							
			}
		}
		 
		 
		 class EmailNotificationListener implements ClientRegistrationListener {

			@Override
			public void onClientAdded(Client c) {
				
				System.out.println("Email has been sent to " + c.getName());
				
			}
		 }
		 
		 listeners.add(new PrintClientListener());
		 listeners.add(new EmailNotificationListener());
			 
	}
	
	
	public Bank(String name){
		
		this.name = name;
	}

	public List<Client> getClients() {
		return Collections.unmodifiableList(clients);
	}
	
	public void setClients(List<Client> list){
		clients = list;
	}
	
	public List<ClientRegistrationListener> getListeners(){
		
		return listeners;
	}
	
	public void addClient(Client client){
		
		clients.add(client);
	}
	
	
	public void removeClient(Client client){
		
		clients.remove(client);
	}
	
	

	@Override
	public void printReport() {
		
		for(Client c : clients ){
			
		  c.printReport();
		 
		}	
	}

	
	public String getName() {
		return name;
	}
	
	public static String getBankByName(Bank bank){
		
		return bank.getName();
	}
	
	public void setName(String string) {
		
		name = string;
	}


	public void removeAllClients(){
			
			clients.clear();
	}
}
