package controllers;

import managers.GerenciadorDeCadeiras;
import models.PlanoDeCurso;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	static PlanoDeCurso plano;

	
	public static Result index(){
		if (plano == null) {
			if (!PlanoDeCurso.find.all().isEmpty()){
				// se ja houver uma entidade salva no BD carrega ela
				//junto com periodos e cadeira dos periodos
				plano = PlanoDeCurso.find.all().get(0);
				plano.atualizaMapaCadeira(plano.getCadeirasAlocadas());
			} else {
				plano = new PlanoDeCurso();
				plano.distribuiCaderas(GerenciadorDeCadeiras.getMapaDeCadeiras());
				plano.save();
			}
		}
		return ok(views.html.index.render(plano));
	}

	public static Result addPeriodo() {
		plano.addPeriodo();
		return ok(views.html.index.render(plano));
	}

	public static Result addCadeira(String cadeira, int periodo){
		plano.addCadeira(cadeira, periodo);
		plano.update();
		return redirect(routes.Application.index());
	}

	public static Result remPeriodo(int periodo) {
		plano.removePeriodo(periodo);
		return redirect(routes.Application.index());
	}

	public static Result remCadeira(String cadeira){
		plano.removeCadeira(cadeira);
		plano.update();
		return redirect(routes.Application.index());
	}
}
