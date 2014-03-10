package controllers;

import java.util.List;

import org.apache.commons.lang3.Validate;

import models.Cadeira;
import models.Usuario;
import static play.data.Form.*; 
import models.PlanoDeCurso;
import models.exceptions.LimiteUltrapassadoException;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	static PlanoDeCurso plano;

	public static Result index(){
		// carrega o plano referente ao usuario logado
		// através da sessão.
		if (plano == null){
			plano = PlanoDeCurso.find.byId(Long.parseLong(session("user")));
			plano.atualizaMapaCadeira(Cadeira.find.all());
		}
		return ok(views.html.index.render(plano));
	}
	
	public static Result login() {
		// se nao tiver nenhum usuario no BD, cria 30 novos usuarios.
		if(Usuario.find.all().isEmpty()){
			GeradorDeUsuario.geraUsuarios();
		}
	    return ok(
	    	views.html.login.render(form(Login.class))
	    );
	}
	
	public static Result cadastrar(){
		if(Usuario.find.all().isEmpty()){
			GeradorDeUsuario.geraUsuarios();
		}
	    return ok(
	    	views.html.cadastrar.render(form(Cadastrar.class))
	    );
	}
	
	public static Result logout() {
		// apaga todas as sessoes e retorna para a pagina de login
		session().clear();
	    return ok(
	    	views.html.login.render(form(Login.class))
	    );
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
	        //session().clear();
	        session("connected", loginForm.get().email);
	        return redirect(
	            routes.Application.index()
	        );
	    }
	}
	
	public static Result verificaUsuario(){
		return cadastrar();
	}
	
	public static String validate(String email, String senha) {
		List<Usuario> u = Usuario.find.where().eq("email", email).eq("senha", senha).findList();
		if(u == null || u.isEmpty()){
			return "Invalid user or password";
		} 
		// estabelece uma sessão para guardar o id do usuario
		session("user", String.valueOf(u.get(0).getPlano().getId()));
	    return null;
	}

	public static Result addCadeira(String cadeira, int periodo){
		try {
			plano.addCadeira(cadeira, periodo);
		} catch (LimiteUltrapassadoException e) {
			return badRequest(e.getMessage());
		}
		plano.update();
		return redirect(routes.Application.index());
	}

	public static Result remCadeira(String cadeira){
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