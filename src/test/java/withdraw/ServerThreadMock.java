package withdraw;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import command.bank.BankCommander;
import command.bank.DBSelectBankCommander;
import command.bank.DBSelectClientCommander;
import command.server.BankServerCommand;
import command.server.DepositServerCommand;
import command.server.ExitServerCommand;
import command.server.ServerConnection;
import command.server.SetAcitveBankServerCommand;
import command.server.SetActiveClientServerCommand;
import command.server.TransferServerCommand;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;

public class ServerThreadMock implements Runnable{

	boolean loop = true;
	AtomicInteger counter;
	ServerConnection serverConnection;
	HashMap<Integer,BankServerCommand> map = new HashMap<>();
	ReentrantLock lock;
		
	
	public ServerThreadMock(Socket socket, AtomicInteger counter, ReentrantLock lock) {
		
		try {
			
			this.counter = counter;
			this.serverConnection = new ServerConnection(socket);
			this.lock = lock;
			
			
			map.put(12, new SetAcitveBankServerCommand(serverConnection));
			map.put(22, new SetActiveClientServerCommand(serverConnection));
			map.put(1, new WithdrawServerCommandMock(serverConnection,lock));
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
			counter.incrementAndGet();
			
			System.out.println("Connection received from " + serverConnection.getSocket().getInetAddress().getHostName());
			
			Integer number = 0;
			
			number =  (Integer)serverConnection.getIn().readObject();
			(map.get(number)).execute();
			updateBankCommander();
							
		} catch (IOException | ClassNotFoundException | DAOException| BankNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | BankException e) {
			e.printStackTrace();
		} finally {
			
			try {
				counter.decrementAndGet();
				serverConnection.getSocket().close();
			}
			catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
		
	}
	
	private void updateBankCommander() throws NumberFormatException, DAOException, IOException, BankNotFoundException{
 		String bankName = BankCommander.getCurrentBank().getName();
 		((DBSelectBankCommander)BankCommander.getMap().get("SetActiveBank")).setBankName(bankName).execute();
 		String clientName = BankCommander.getCurrentClient().getName();
 		((DBSelectClientCommander)BankCommander.getMap().get("SetActiveClient")).setClientName(clientName).execute();
	}
}
