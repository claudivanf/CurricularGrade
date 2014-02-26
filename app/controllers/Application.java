package controllers;

import models.Cadeira;
import models.PlanoDeCurso;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	static PlanoDeCurso plano;

	public static Result index() throws Exception {
		if (plano == null) {
			if (!PlanoDeCurso.find.all().isEmpty()){
				// se ja houver uma entidade salva no BD carrega ela
				plano = PlanoDeCurso.find.all().get(0);
				plano.distribuiCaderas(Cadeira.find.where().eq("plano_id", plano.getId()).findList());
			} else {
				plano = new PlanoDeCurso();
				plano.save();
			}
		}
		return ok(views.html.index.render(plano));
	}

	public static Result addPeriodo() {
		plano.addPeriodo();
		return ok(views.html.index.render(plano));
	}

	public static Result addCadeira(String cadeira, int periodo)
			throws NumberFormatException, Exception {
		plano.addCadeira(cadeira, periodo);
		plano.update();
		return redirect(routes.Application.index());
	}

	public static Result remPeriodo(int periodo) {
		plano.removePeriodo(periodo);
		return redirect(routes.Application.index());
	}

	public static Result remCadeira(String cadeira) throws Exception {
		plano.removeCadeira(cadeira);
		plano.update();
		return redirect(routes.Application.index());
	}
}
