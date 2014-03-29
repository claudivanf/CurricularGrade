package validators;

import exceptions.LimiteDeCreditosException;


public interface ValidadorDePeriodo {
	public boolean valida(int creditos) throws LimiteDeCreditosException;
}
