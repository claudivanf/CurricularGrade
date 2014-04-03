package generators;

import java.util.List;

import models.Cadeira;
import models.PlanoDeCurso;
import models.Usuario;
import exceptions.LimiteDeCreditosException;
import exceptions.PeriodoCursandoException;

public class GerenciadorDeUsuario {

	private static String[] nomes = { "joao", "jose", "maria", "joana", "bruno" };

	public static void geraUsuarios() throws LimiteDeCreditosException, PeriodoCursandoException {
		for (int i = 0; i <= 30; i++) {
			String nome = nomes[i % 5] + i / 5;
			String email = nome + i / 5 + "@gmail.com";
			String senha = "123";
			Usuario u = new Usuario(email, nome, senha);
			u.getPlano().setGrade("OFICIAL");
			u.getPlano().distribuiCaderas(Cadeira.find.where().eq("grade", "OFICIAL").findList());
			u.getPlano().setPeriodoCursando(3);
			// salva usuario no BD com plano e tudo
			u.save();
		}
	}
	
	public static List<Usuario> getUsuariosCadastrados(){
		return Usuario.find.all();
	}

	public static List<Usuario> find(String email) {
		return Usuario.find.where().eq("email", email).findList();
		
	}
	
	public static PlanoDeCurso getPlanoDeCurso(String email){
		Usuario u = Usuario.find.where().eq("email", email).findUnique();
		return u.getPlano();
	}

}
