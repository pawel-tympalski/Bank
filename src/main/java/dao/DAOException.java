package dao;

public class DAOException extends Exception {

	private static final long serialVersionUID = 7335323481724653726L;
	String message;
	
	public DAOException(String message){
		
		this.message = message;
		
	}
	
	public String  getMessage(){
		
		return message;
	}
}
