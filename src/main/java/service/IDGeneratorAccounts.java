package service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import dao.ClientDAOImpl;
import dao.DAOException;
import exception.BankNotFoundException;
import model.AbstractAccount;
import model.Client;

public class IDGeneratorAccounts {
	private static Random numberGenerator = new Random();
	private static Set<Integer> idPool = new TreeSet<Integer>();
	
	
	public static int generateID(String bankName) throws DAOException, BankNotFoundException {
		ClientDAOImpl c = new ClientDAOImpl();
		List<Client>  clients = c.getAllClients(bankName);
		for(Client client : clients){
			for(AbstractAccount account : client.getAccounts()){
				idPool.add(account.getId());
			}
		}
		
		int number = 1 + numberGenerator.nextInt(1000);
		while (idPool.contains(number)) {
			number = 1 + numberGenerator.nextInt(1000);
		}
		idPool.add(number);
		return number;
	}

	public static  void getBackNumberID(Client client) {
		int number = client.getId();
		idPool.remove(number);
	}
}	