package models.validators;

import models.Cadeira;
import models.Periodo;
import models.exceptions.LimiteDeCreditosException;


public interface ValidadorDePeriodo {
	public boolean valida(Periodo periodo) throws LimiteDeCreditosException;
}
