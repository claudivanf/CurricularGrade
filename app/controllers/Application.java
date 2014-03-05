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

	static Long idUsuario;
	static PlanoDeCurso plano;

	public static Result index(){
		plano = PlanoDeCurso.find.byId(Long.parseLong(session("user")));
		if (plano == null) {
			if (!PlanoDeCurso.find.all().isEmpty()){
				// se ja houver uma entidade salva no BD carrega ela
				//junto com periodos e cadeira dos periodos
				plano = PlanoDeCurso.find.all().get(0);
				plano.atualizaMapaCadeira(Cadeira.find.all());
			} else {
				// se não houver cria um novo plano, distribui as cadeiras
				// de modo igual à grade original de CC e salva tudo no BD. 
				plano = new PlanoDeCurso();
				plano.distribuiCaderas(Cadeira.find.all());
				plano.save();
			}
		} else{
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
	
	public static Result logout() {
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
	
	public static String validate(String email, String senha) {
		List<Usuario> u = Usuario.find.where().eq("email", email).eq("senha", senha).findList();
		if(u == null || u.isEmpty()){
			return "Invalid user or password";
		} 
		idUsuario = u.get(0).getPlano().getId();
		session("user", String.valueOf(idUsuario));
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
}