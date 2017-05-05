package model;
import exception.BankException;
import exception.OverDraftLimitExceededException;

public class CheckingAccount extends AbstractAccount{
	
	private static final long serialVersionUID = 2343140130961598669L;
	private float overdraft;
	
	public CheckingAccount(float balance,int accountId , float overdraft) {
		super(balance, accountId);
		this.overdraft = overdraft;
	}



	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(overdraft);
		return result;
	}

	@Override
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("Account name " + this.getClass().getName());
		sb.append("Account balance" + getBalance());
		sb.append("Overdraft " + getOverDraft());
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckingAccount other = (CheckingAccount) obj;
		if (Float.floatToIntBits(overdraft) != Float.floatToIntBits(other.overdraft))
			return false;
		return true;
	}


	public void setOverdraft(float overdraft) {
		this.overdraft = overdraft;
	}
	
	public float getOverDraft(){
		
		return overdraft; 
	}

	@Override
	public void printReport() {
		System.out.println("type of the account" + this.getClass().getName()+ " balance" + getBalance());
	}

	
	@Override
	public void withdraw(float amount) throws BankException {
		
		
		amount = decimalValue(amount);
		if(getBalance() + overdraft < amount){
			
			throw new OverDraftLimitExceededException(amount, getOverDraft());
		}
		else {
			
			setBalance(getBalance() - amount);
		}
		
	}
	@Override	
	public String getAccountType(){
			
			return "c";
	}
}
