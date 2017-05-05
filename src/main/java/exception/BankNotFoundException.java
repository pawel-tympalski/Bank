package exception;

public class BankNotFoundException extends Exception {

	private static final long serialVersionUID = -2755964003175819625L;
	private String name;
	
	public BankNotFoundException(String name){
		this.name = name;
	}
	
	public String getMessage(){
		
		return "Bank " + name + " does not exist";
		
	}
}
