package models.validators;

import models.exceptions.LimiteDeCreditosException;


public interface ValidadorDePeriodo {
	public boolean valida(int creditos) throws LimiteDeCreditosException;
}
