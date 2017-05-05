package exception;

public class ClientExistException extends Exception {
	
	private static final long serialVersionUID = 7182956325234728000L;
	private String text ="Client does not exist, please ... ";
	
	public String getMessage(){
		
		return text;
	}
}
