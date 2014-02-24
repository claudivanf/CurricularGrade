package models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import play.db.ebean.Model;

/**
 * Entidade que representa um período
 */
public class Periodo extends Model{

	private static final long serialVersionUID = 1L;
	
	private int numero;

	private Map<String, Cadeira> cadeiras;

	public Periodo(int numeroDoPeriodo) {
		this.numero = numeroDoPeriodo;
		cadeiras = new HashMap<String, Cadeira>();
	}
	
	public static Finder<Long,Periodo> find = new Finder<Long,Periodo>(
		    Long.class, Periodo.class
	); 

	public void addCadeira(Cadeira cadeira){
		cadeiras.put(cadeira.getNome(), cadeira);
	}

	public void removerCadeira(Cadeira cadeira) {
		cadeiras.remove(cadeira.getNome());
	}

	public int getDificuldadeTotal() {
		int difi = 0;
		for (Cadeira c : getCadeiras().values()) {
			difi += c.getDificuldade();
		}
		return difi;
	}

	/**
	 * Calcula o total de Créditos do Periodo
	 * 
	 * Responsabilidade Atribuída seguindo o padrão Information Expert
	 */
	public int getCreditos() {
		int sum = 0;
		for (Cadeira c : getCadeiras().values()) {
			sum += c.getCreditos();
		}
		return sum;
	}

	public Map<String, Cadeira> getCadeiras() {
		return cadeiras;
	}
	
	public Collection<Cadeira> getListaCadeiras(){
		return getCadeiras().values();
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero){
		this.numero = numero;
	}

	public Cadeira getCadeira(String cadeira) {
		return cadeiras.get(cadeira);
	}
	
	public static void create(Periodo p) {
		p.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}
	
	public static void atualizar(Long id) {
		Periodo p = find.ref(id);
		p.update();
	}
}