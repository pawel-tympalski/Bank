package exception;

public class NotEnoughFundsException extends BankException {

	private static final long serialVersionUID = 1L;
	private float amount;
	
	public float getAmount(){
		
		return amount;
	}
	
	public NotEnoughFundsException(float amount){
		
		this.amount = amount;
	}
}
