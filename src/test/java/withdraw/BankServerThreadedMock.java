package withdraw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import service.BankServerMonitor;


public class BankServerThreadedMock extends Thread {
	ServerSocket serverSocket;
	ExecutorService pool = Executors.newFixedThreadPool(10);
	AtomicInteger counter = new AtomicInteger();
	ReentrantLock lock = new ReentrantLock();
	@Override
	public void run()  {
		
		BankServerMonitor bsm = new BankServerMonitor(counter);
		bsm.start();

		try {
			serverSocket = new ServerSocket(4000);
		
		while (true) {

			System.out.println("I am waiting for connection");
			Socket socket = serverSocket.accept();
			System.out.println("accepted");
			counter.getAndIncrement();
			ServerThreadMock stm = new ServerThreadMock(socket, counter, lock);
			pool.execute(stm);
		}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
}
