package service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BankServerMonitor  {
	AtomicInteger atomic;
	
	public BankServerMonitor(AtomicInteger atomic){
		
		this.atomic = atomic;
	}


	public void start(){
		
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		 
		exec.scheduleAtFixedRate(new Runnable() {
				
			@Override
			public void run() {
				System.out.println("Number of clients waiting for connection " + atomic);
			}
		}, 0, 10, TimeUnit.SECONDS);
	}
}
