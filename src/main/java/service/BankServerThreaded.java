package service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import command.bank.BankCommander;
import command.bank.DBSelectBankCommander;
import command.bank.DBSelectClientCommander;
import dao.BaseDaoImpl;
import dao.DAOException;
import exception.BankNotFoundException;

public class BankServerThreaded extends Thread {
	ServerSocket serverSocket;
	ExecutorService pool = Executors.newFixedThreadPool(10);
	AtomicInteger counter = new AtomicInteger();
	ReentrantLock lock = new ReentrantLock();
	@Override
	public void run()  {
		
		BankServerMonitor bsm = new BankServerMonitor(counter);
		bsm.start();

		try{
			Database database = new Database(new BaseDaoImpl());
		
			database.insertSampleDataToDatabase();
		
			((DBSelectBankCommander)BankCommander.getMap().get("SetActiveBank")).setBankName("ACL").execute();
			((DBSelectClientCommander)BankCommander.getMap().get("SetActiveClient")).setClientName("Anna Smith").execute();
		
			serverSocket = new ServerSocket(4000);
		
			while (true) {

				System.out.println("I am waiting for connection");
				Socket clientSocket = serverSocket.accept();
				counter.getAndIncrement();
				pool.execute(new ServerThread(clientSocket, counter, lock));
			}
		} catch (IOException | ClassNotFoundException | SQLException | DAOException | BankNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
	 
	public static void main(String[] args){
		
			new BankServerThreaded().start();
		
	}
	
	
}
