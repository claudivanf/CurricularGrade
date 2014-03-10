package models.validators;

import models.Periodo;
import models.exceptions.LimiteDeCreditosException;

public class ValidadorMin implements ValidadorDePeriodo{

	public static final int MINIMO_DE_CREDITOS = 12;

	@Override
	public boolean valida(Periodo periodo)
			throws LimiteDeCreditosException {
		if (periodo.getCreditos() < MINIMO_DE_CREDITOS) {
			throw new LimiteDeCreditosException("Créditos insuficientes");
		}
		return true;
	}

}
