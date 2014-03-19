package controllers;

import static play.data.Form.form;

import java.util.List;

import models.Cadeira;
import models.PlanoDeCurso;
import models.Usuario;
import models.exceptions.LimiteDeCreditosException;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	static PlanoDeCurso plano;

	public static Result index() {
		// carrega o plano referente ao usuario logado
		// através da sessão.
		plano = PlanoDeCurso.find.byId(Long.parseLong(session("user")));
		plano.atualizaMapaCadeira(Cadeira.find.all());
		plano.atualizaPeriodoAtual();
		return ok(views.html.index.render(plano));
	}

	public static Result login() throws LimiteDeCreditosException {
		// se nao tiver nenhum usuario no BD, cria 30 novos usuarios.
		if (Usuario.find.all().isEmpty()) {
			GeradorDeUsuario.geraUsuarios();
		}
		return ok(views.html.login.render(form(Login.class)));
	}

	public static Result cadastrar() throws LimiteDeCreditosException {
		if (Usuario.find.all().isEmpty()) {
			GeradorDeUsuario.geraUsuarios();
		}
		return ok(views.html.cadastrar.render(form(Cadastrar.class)));
	}

	public static Result logout() {
		// apaga todas as sessoes e retorna para a pagina de login
		session().clear();
		return ok(views.html.login.render(form(Login.class)));
	}

	public static Result atualizaPeriodo() {
		Form<Cadastrar> cadastroForm = form(Cadastrar.class).bindFromRequest();
		try {
			int periodo = cadastroForm.get().periodo;
			if (!plano.setPeriodoCursando(periodo)) {
				flash("fail", "Periodo Invalido");
			}
		} catch (IllegalStateException e) {
			flash("fail", "Periodo Invalido - Não pode ser uma String!");
		}
		plano.update();
		return index();
	}

	public static Result authenticate() {

		Form<Login> loginForm = form(Login.class).bindFromRequest();
		String email = loginForm.get().email;
		String senha = loginForm.get().password;

		String erro = validate(email, senha);

		if (loginForm.hasErrors() || erro != null) {
			flash("fail", erro);
			return badRequest(views.html.login.render(loginForm));
		} else {
			// session().clear();
			session("connected", loginForm.get().email);
			return redirect(routes.Application.index());
		}
	}

	public static Result verificaUsuario() throws LimiteDeCreditosException {
		Form<Cadastrar> cadastroForm = form(Cadastrar.class).bindFromRequest();
		String nome = cadastroForm.get().nome;
		String email = cadastroForm.get().email;
		String senha = cadastroForm.get().password;
		int periodo = cadastroForm.get().periodo;
		List<Usuario> u = Usuario.find.where().eq("email", email).findList();
		if (!u.isEmpty()) {
			flash("fail", "Email Já Cadastrado");
			return cadastrar();
		}
		if (periodo < 1 || periodo > 10) {
			flash("fail", "Periodo Invalido");
			return cadastrar();
		}
		Usuario usuario = new Usuario(email, nome, senha);
		usuario.getPlano().distribuiCaderas(Cadeira.find.all());
		usuario.getPlano().setPeriodoCursando(periodo);
		usuario.save();
		flash("sucesso", "Usuario Cadastrado Com Sucesso");
		return cadastrar();
	}

	public static String validate(String email, String senha) {
		List<Usuario> u = Usuario.find.where().eq("email", email)
				.eq("senha", senha).findList();
		if (u == null || u.isEmpty()) {
			return "Invalid user or password";
		}
		// estabelece uma sessão para guardar o id do usuario
		session("user", String.valueOf(u.get(0).getPlano().getId()));
		return null;
	}

	public static Result addCadeira(String cadeira, int periodo) {
		try {
			plano.addCadeira(cadeira, periodo);
		} catch (LimiteDeCreditosException e) {
			flash("fail", e.getMessage());
			return index();
		}
		plano.update();

		return redirect(routes.Application.index());
	}

	public static Result remCadeira(String cadeira) {
		plano.removeCadeira(cadeira);
		plano.update();
		return redirect(routes.Application.index());
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