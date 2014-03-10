package models.exceptions;

public class LimiteDeCreditosException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public LimiteDeCreditosException(String message){
		super(message);
	}

	@Override
	public String toString(){
		return this.getMessage();
	}
}
