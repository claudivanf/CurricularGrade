package models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import managers.GerenciadorDeCadeiras;
import play.db.ebean.Model;

/**
 * Entidade que representa um período
 */
@Entity
public class Periodo extends Model{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(name="numero_periodo")
	private int numero;

	@OneToMany(cascade=CascadeType.ALL)   //um periodo tem varias cadeiras
	@JoinTable
	private Map<String, Cadeira> cadeiras;
	
	private PlanoDeCurso plano;

	public Periodo(int numeroDoPeriodo) {
		this.numero = numeroDoPeriodo;
		cadeiras = new HashMap<String, Cadeira>();
	}
	
	public Periodo(int numeroDoPeriodo, PlanoDeCurso plano) {
		this(numeroDoPeriodo);
		this.plano = plano;
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
		if (cadeiras == null){
			cadeiras = GerenciadorDeCadeiras.getCadeirasPorPeriodo(numero);
		}
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
	
	public PlanoDeCurso getPlano() {
		return plano;
	}

	public void setPlano(PlanoDeCurso plano) {
		this.plano = plano;
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