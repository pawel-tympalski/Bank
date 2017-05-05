package model;

import exception.BankException;
import service.Report;

public interface Account  extends Report {
	
	float getBalance();
	
	void deposit(float value);
	
	void withdraw(float value) throws BankException;
	
	void setBalance(float value);

	String getAccountType();
	
}
