package controllers;

import static play.data.Form.form;
import exceptions.LimiteDeCreditosException;
import exceptions.PeriodoCursandoException;
import generators.GeradorDeUsuario;

import java.util.List;

import models.Cadeira;
import models.Usuario;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class UsuarioController extends Controller {

	static Usuario usuario;

	public static Result login() throws LimiteDeCreditosException, PeriodoCursandoException{
		// se nao tiver nenhum usuario no BD, cria 30 novos usuarios.
		if (Usuario.find.all().isEmpty()) {
			GeradorDeUsuario.geraUsuarios();
		}
		return ok(views.html.Usuario.login.render(form(Login.class)));
	}

	public static Result cadastrar() throws LimiteDeCreditosException, PeriodoCursandoException {
		if (Usuario.find.all().isEmpty()) {
			GeradorDeUsuario.geraUsuarios();
		}
		return ok(views.html.Usuario.cadastrar.render(form(Cadastrar.class)));
	}

	public static Result logout() throws LimiteDeCreditosException, PeriodoCursandoException {
		// apaga todas as sessoes e retorna para a pagina de login
		session().clear();
		return login();
	}

	public static Result authenticate() {

		Form<Login> loginForm = form(Login.class).bindFromRequest();
		String email = loginForm.get().email;
		String senha = loginForm.get().password;

		String erro = validate(email, senha);
		
		if (loginForm.hasErrors() || erro != null) {
			flash("fail", erro);
			return badRequest(views.html.Usuario.login.render(loginForm));
		} else {
			// session().clear();
			Usuario u = Usuario.find.where().eq("email", email)
					.eq("senha", senha).findUnique();
			session("connected", u.getNome());
			return redirect(routes.PlanoDeCursoController.index());
		}
	}

	public static Result verificaUsuario() throws LimiteDeCreditosException,PeriodoCursandoException {
		Form<Cadastrar> cadastroForm = form(Cadastrar.class).bindFromRequest();
		String nome = cadastroForm.get().nome;
		String email = cadastroForm.get().email;
		String senha = cadastroForm.get().password;
		try {
			int periodo = cadastroForm.get().periodo;
			List<Usuario> u = Usuario.find.where().eq("email", email).findList();
			if (!u.isEmpty()) {
				flash("fail", "Email Já Cadastrado");
				return cadastrar();
			}
			usuario = new Usuario(email, nome, senha);
			usuario.getPlano().distribuiCaderas(Cadeira.find.all());
			usuario.getPlano().setPeriodoCursando(periodo);
			usuario.save();
			flash("sucesso", "Usuario Cadastrado Com Sucesso");
		} catch (IllegalStateException e) {
			flash("fail", "Periodo Invalido - Não pode ser uma String!");
		} catch (PeriodoCursandoException e) {
			flash("fail", e.getMessage());
		}
		return cadastrar();
	}

	public static String validate(String email, String senha) {
		List<Usuario> u = Usuario.find.where().eq("email", email)
				.eq("senha", senha).findList();
		if (u == null || u.isEmpty()) {
			return "Invalid user or password";
		}
		// estabelece uma sessão para guardar o id do usuario
		session("user_plano_id", String.valueOf(u.get(0).getPlano().getId()));
		return null;
	}

	public static class Login {
		public String email;
		public String password;
	}

	public static class Cadastrar {
		public String nome;
		public String email;
		public String password;
		public int periodo;
	}
}