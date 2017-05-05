package model;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class AbstractAccount implements Account, Serializable{
	
	private static final long serialVersionUID = -9167153923448993988L;
	private float balance;
	private int accountId;
	
	public AbstractAccount(float balance,int accountId) {
		this.balance = balance;
		this.accountId = accountId;
	}

	
	
	@Override
	public int hashCode() {
		return accountId;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAccount other = (AbstractAccount) obj;
		if (Float.floatToIntBits(balance) != Float.floatToIntBits(other.balance))
			return false;
		return true;
	}

	public float decimalValue(float amount){
		BigDecimal bigDecimal = new BigDecimal(amount);
        bigDecimal = bigDecimal.setScale(2,
                BigDecimal.ROUND_HALF_UP);
		
		return bigDecimal.floatValue();
	}

	@Override
	public float getBalance() {
		return balance;
	}

	@Override
	public void deposit(float amount) {
		amount = decimalValue(amount);
		balance +=  amount;
	}
	
	@Override
	public void setBalance(float balance){
		this.balance = balance;
	}
	
	public String getAccountType(){
		return null;
	};
	
	
	public int getId(){
		return accountId;
	}
	
	public void setAccountID(int accountId){
		
		this.accountId = accountId;
		
	}
}
