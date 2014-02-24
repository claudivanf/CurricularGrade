package controllers;

import models.PlanoDeCurso;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	static PlanoDeCurso plano;

	public static Result index() throws Exception {
		if (plano == null) {
			plano = new PlanoDeCurso();
			try{
				plano.save();
			}catch(Exception e){
				System.out.println(e.getMessage());
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

		return redirect(routes.Application.index());
	}

	public static Result remPeriodo(int periodo) {
		plano.removePeriodo(periodo);
		return redirect(routes.Application.index());
	}

	public static Result remCadeira(String cadeira) throws Exception {
		plano.removeCadeira(cadeira);
		return redirect(routes.Application.index());
	}
}
