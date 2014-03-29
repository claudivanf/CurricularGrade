package generators;

import exceptions.LimiteDeCreditosException;
import exceptions.PeriodoCursandoException;
import models.Cadeira;
import models.Usuario;

public class GeradorDeUsuario {

	private static String[] nomes = { "joao", "jose", "maria", "joana", "bruno" };

	public static void geraUsuarios() throws LimiteDeCreditosException, PeriodoCursandoException {
		for (int i = 0; i <= 30; i++) {
			String nome = nomes[i % 5] + i / 5;
			String email = nome + i / 5 + "@gmail.com";
			String senha = "123";
			Usuario u = new Usuario(email, nome, senha);
			u.getPlano().distribuiCaderas(Cadeira.find.all());
			u.getPlano().setPeriodoCursando(3);
			// salva usuario no BD com plano e tudo
			u.save();
		}
	}
}
