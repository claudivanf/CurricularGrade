package controllers;

import static play.data.Form.form;
import models.Cadeira;
import models.PlanoDeCurso;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import exceptions.LimiteDeCreditosException;
import exceptions.PeriodoCursandoException;
import generators.GerenciadorDeUsuario;

public class PlanoDeCursoController extends Controller {

	static PlanoDeCurso plano;

	public PlanoDeCursoController(PlanoDeCurso plano){
		this.plano = plano;
	}

	public static Result index() throws PeriodoCursandoException{
		// carrega o plano referente ao usuario logado
		// através da sessão.
		plano = PlanoDeCurso.find.byId(Long.parseLong(session("user_plano_id")));
		if (plano == null){
			session().clear();
			return redirect(routes.UsuarioController.login());
		}
		plano.atualizaMapaCadeira(Cadeira.find.where().eq("grade",plano.getGrade()).findList());
		plano.atualizaValidadoresPeriodos();
		return ok(views.html.Plano.planoEdit.render(
			plano, 
			GerenciadorDeUsuario.getUsuariosCadastrados(),
					views.html.modalPlano.render(plano),
					views.html.modalDeErro.render()
			));
	}
	
	public static Result mostraPlanoDoUsuario(String usuarioEmail){
		PlanoDeCurso pl = GerenciadorDeUsuario.getPlanoDeCurso(usuarioEmail);
		pl.atualizaMapaCadeira(Cadeira.find.where().eq("grade",pl.getGrade()).findList());
		return ok(views.html.Plano.planoRead.render(
					pl ));
	}

	public static Result atualizaPeriodo() throws PeriodoCursandoException{
		Form<PeriodoUpdate> periodoForm = form(PeriodoUpdate.class).bindFromRequest();
		try {
			int periodo = Integer.parseInt(periodoForm.data().values().toArray()[0].toString());
			plano.setPeriodoCursando(periodo);
		} catch (IllegalStateException e) {
			flash("fail", "Periodo Invalido - Não pode ser uma String!");
		} catch (PeriodoCursandoException e) {
			flash("fail", e.getMessage());
		}
		plano.update();
		return index();
	}

	public static Result addCadeira(String cadeira, int periodo) throws PeriodoCursandoException {
		try {
			plano.addCadeira(cadeira, periodo);
			plano.update();
		} catch (LimiteDeCreditosException e) {
			flash("fail", e.getMessage() );
		}
		return index();
	}

	public static Result remCadeira(String cadeira) throws PeriodoCursandoException {
		try {
			plano.removeCadeira(cadeira);
		} catch (LimiteDeCreditosException e) {
			flash("fail", e.getMessage() + ": Alguma cadeira nao pôde ser removida, " +
					"pois possui o periodo com mínimo de credidos insuficientes");
		}
		plano.update();
		return index();
	}

	public static class PeriodoUpdate {
		public int periodo;
	}
}