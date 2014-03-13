package models.validators;

import javax.naming.LimitExceededException;

import models.Periodo;
import models.exceptions.LimiteDeCreditosException;

public class ValidadorMax implements ValidadorDePeriodo {

	public static final int MAXIMO_DE_CREDITOS = 28;

	public boolean valida(int novaQuantidadeCreditos) {
		boolean isNovaQtdCreditosValida = true;
		if (novaQuantidadeCreditos > MAXIMO_DE_CREDITOS) {
			isNovaQtdCreditosValida = false;
		}
		return isNovaQtdCreditosValida;
	}

}
