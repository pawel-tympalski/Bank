package service;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import command.server.BankServerCommand;
import command.server.BankStatisticsServerCommand;
import command.server.DepositServerCommand;
import command.server.ExitServerCommand;
import command.server.ServerConnection;
import command.server.SetAcitveBankServerCommand;
import command.server.SetActiveClientServerCommand;
import command.server.TransferServerCommand;
import command.server.WithdrawServerCommand;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;


public class ServerThread implements Runnable{

	AtomicInteger counter;
	ServerConnection serverConnection;
	HashMap<Integer,BankServerCommand> map = new HashMap<>();
	ReentrantLock lock;
	
	public ServerThread(Socket socket, AtomicInteger counter, ReentrantLock lock) {
		
		try {
			
			this.counter = counter;
			this.serverConnection = new ServerConnection(socket);
			this.lock = lock;
			
			
			map.put(12, new SetAcitveBankServerCommand(serverConnection));
			map.put(22, new SetActiveClientServerCommand(serverConnection));
			map.put(30, new BankStatisticsServerCommand(serverConnection));
			map.put(1, new WithdrawServerCommand(serverConnection,lock));
			map.put(2, new DepositServerCommand(serverConnection,lock));
			map.put(3, new TransferServerCommand(serverConnection,lock));
			map.put(4, new ExitServerCommand(serverConnection));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
			
	}
	
	
	@Override
	public void run() {
		try {
			
			
			System.out.println("Connection received from " + serverConnection.getSocket().getInetAddress().getHostName());
			
			while(true){
				Integer number = 0;
				try{
					System.out.println(serverConnection);
				 number =  (Integer)serverConnection.getIn().readObject();
				}
				catch(EOFException e){
					
				}
				
				map.get(new Integer(number)).execute();
				
				if(number == 4){
					break;
				}
				
			}	
							
		} catch (IOException | ClassNotFoundException | DAOException| BankNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | BankException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				counter.decrementAndGet();
				serverConnection.getSocket().close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
