package models.validators;

public class ValidadorMin implements ValidadorDePeriodo {

	public static final int MINIMO_DE_CREDITOS = 12;

	public boolean valida(int novaQuantidadeCreditos) {
		boolean isNovaQtdCreditosValida = true;
		if (novaQuantidadeCreditos < MINIMO_DE_CREDITOS) {
			isNovaQtdCreditosValida = false;
		}
		return isNovaQtdCreditosValida;
	}
}
