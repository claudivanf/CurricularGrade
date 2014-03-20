package models.exceptions;

public class PeriodoCursandoException  extends Exception {

	private static final long serialVersionUID = 1L;
	
	public PeriodoCursandoException(String message){
		super(message);
	}

	@Override
	public String toString(){
		return this.getMessage();
	}
}
