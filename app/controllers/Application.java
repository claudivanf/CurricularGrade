package controllers;

import models.Cadeira;
import models.PlanoDeCurso;
import models.exceptions.LimiteUltrapassadoException;
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
				// se não houver cria um novo plano, distribui as cadeiras
				// de modo igual à grade original de CC e salva tudo no BD. 
				plano = new PlanoDeCurso();
				plano.distribuiCaderas(Cadeira.find.all());
				plano.save();
			}
		}
		return ok(views.html.index.render(plano));
	}

	public static Result addCadeira(String cadeira, int periodo) throws LimiteUltrapassadoException{
		plano.addCadeira(cadeira, periodo);
		plano.update();
		return redirect(routes.Application.index());
	}

	public static Result remCadeira(String cadeira){
		plano.removeCadeira(cadeira);
		plano.update();
		return redirect(routes.Application.index());
	}
}
