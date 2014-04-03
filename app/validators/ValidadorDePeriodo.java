package validators;

public interface ValidadorDePeriodo {
	
	/**
	 * Retorna se a operação é válida ou não para com o período.
	 * 
	 * @param creditos
	 */
	public boolean valida(int creditos);
}
