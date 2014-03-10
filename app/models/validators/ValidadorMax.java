package models.validators;

import models.Periodo;
import models.exceptions.LimiteDeCreditosException;

public class ValidadorMax implements ValidadorDePeriodo{

	public static final int MAXIMO_DE_CREDITOS = 28;
	
	@Override
	public boolean valida(Periodo periodo) throws LimiteDeCreditosException {
		if(periodo.getCreditos() > MAXIMO_DE_CREDITOS){
			throw new LimiteDeCreditosException("Limite de Créditos Ultrapassado");
		}
		return true;
	}

}
