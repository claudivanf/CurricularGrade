package validators;

public class ValidadorMax implements ValidadorDePeriodo {

	public static final int MAXIMO_DE_CREDITOS = 28;

	public boolean valida(int creditos) {
		return creditos <= MAXIMO_DE_CREDITOS;
	}
}
