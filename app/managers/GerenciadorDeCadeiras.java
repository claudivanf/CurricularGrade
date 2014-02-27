package managers;

import java.util.List;

import models.Cadeira;

/**
 * Gerenciador criado seguindo o padrão Pure Fabrication.
 */
public class GerenciadorDeCadeiras {

	// TODO PADRÃO DE PROJETO: CONTROLLER - essa classe é responsável por
	// controlar a adição de cadeiras no mapa.

	public static List<Cadeira> getMapaDeCadeiras() {
		List<Cadeira> cadeiras = Cadeira.find.all();
		return cadeiras;
	}
	
	public static void atualizaRelacionamento(Cadeira c, int periodo){
		
	}
}
