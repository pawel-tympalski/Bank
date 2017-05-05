package exception;

public class OverDraftLimitExceededException extends NotEnoughFundsException {

	private static final long serialVersionUID = 1765386681950886398L;
	private float overdraft;
	
	public OverDraftLimitExceededException(float amount, float overdraft) {
		super(amount);
		this.overdraft = overdraft;
		
	}
	
	public float getOverDraft(){
		
		return overdraft;
	}

}
