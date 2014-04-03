package validators;

public class ValidadorMin implements ValidadorDePeriodo {

	public static final int MINIMO_DE_CREDITOS = 12;

	public boolean valida(int creditos) {
		return creditos >= MINIMO_DE_CREDITOS;
	}
}
