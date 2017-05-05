package model;
import exception.BankException;
import exception.NotEnoughFundsException;

public class SavingAccount  extends AbstractAccount  {
	private static final long serialVersionUID = 4306474069277494989L;

	public SavingAccount(float balance, int accountId){
		
		super(balance, accountId);
	}
	
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("Account name " + this.getClass().getName());
		sb.append("Account balance" + getBalance());
		
		return sb.toString();
		
	}
	
	
	
	@Override
	public synchronized  void  withdraw(float amount) throws BankException {
		
		
		amount = decimalValue(amount);
		
		if (getBalance() < amount){
			throw new NotEnoughFundsException(amount);
		}
		else {
			setBalance(getBalance() - amount);
		}
		
	}

	@Override
	public void printReport() {
		
		System.out.println("type of the account" + this.getClass().getName()); 
	}

	@Override	
	public String getAccountType(){
			
			return "s";
		}	
}
