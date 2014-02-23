package managers;

import java.util.ArrayList;
import java.util.List;

import models.Periodo;
import models.PlanoDeCurso;

import com.avaje.ebean.Ebean;

public class GerenciadorDePeriodos {
	
	public static void criaPeriodos(PlanoDeCurso plano){
		for (int i = 1; i <= 10; i++){
			if (carregaPeriodoPorId(i) == null) {
				//Periodo p = new Periodo(i);	
				//p.save();
				plano.addPeriodo(i);
			}
		}
	}
	
	public static List<Periodo> criaPeriodos(){
		List<Periodo> periodos = new ArrayList<Periodo>();
		for (int i = 1; i <= 10; i++){
				//Periodo p = new Periodo(i);	
				//p.save();
				//periodos.add(p);
		}
		return periodos;
	}
	
	/**
	 * Carrega determinado periodo do banco de dados
	 * 
	 * @param idPeriodo
	 */
	public static Periodo carregaPeriodoPorId(int idPeriodo){
		return Ebean.find(Periodo.class).where().eq("numero_periodo", idPeriodo).findUnique();
	}
}
