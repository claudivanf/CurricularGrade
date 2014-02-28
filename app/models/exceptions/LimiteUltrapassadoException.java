package models.exceptions;

public class LimiteUltrapassadoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public LimiteUltrapassadoException(String message){
		super(message);
	}

	@Override
	public String toString(){
		return this.getMessage();
	}
}
