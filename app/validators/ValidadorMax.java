package validators;

import exceptions.LimiteDeCreditosException;

public class ValidadorMax implements ValidadorDePeriodo {

	public static final int MAXIMO_DE_CREDITOS = 28;

	public boolean valida(int creditos) throws LimiteDeCreditosException {
		if (creditos > MAXIMO_DE_CREDITOS) {
			throw new LimiteDeCreditosException(
					"Limite de Créditos Ultrapassado");
		}
		return true;
	}

}
