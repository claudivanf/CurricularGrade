package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import models.validators.ValidadorDePeriodo;
import play.db.ebean.Model;

/**
 * Entidade que representa um período
 */
@Entity
public class Periodo extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	private int numeroDoPeriodo;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "periodo_cadeira", joinColumns = { @JoinColumn(name = "fk_periodo") }, inverseJoinColumns = { @JoinColumn(name = "fk_cadeira") })
	private List<Cadeira> cadeiras;

	private List<ValidadorDePeriodo> validadores;

	public static Finder<Long, Periodo> find = new Finder<Long, Periodo>(
			Long.class, Periodo.class);

	public Periodo() {
		cadeiras = new ArrayList<Cadeira>();
		validadores = new ArrayList<ValidadorDePeriodo>();
	}

	public Periodo(int numeroDoPeriodo) {
		this();
		this.numeroDoPeriodo = numeroDoPeriodo;
	}

	public Periodo(int numeroDoPeriodo, List<Cadeira> listaDisciplinasDoPeriodo) {
		this(numeroDoPeriodo);
		for (Cadeira cadeira : listaDisciplinasDoPeriodo) {
			this.addCadeira(cadeira);
		}
	}

	public Long getId() {
		return id;
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

	public void addCadeira(Cadeira cadeira) {
		cadeiras.add(cadeira);
	}

	public boolean removerCadeira(Cadeira cadeira) {
		return cadeiras.remove(cadeira);
	}

	public int getDificuldadeTotal() {
		int difi = 0;
		for (Cadeira c : getCadeiras()) {
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
		for (Cadeira c : getCadeiras()) {
			sum += c.getCreditos();
		}
		return sum;
	}

	public List<Cadeira> getCadeiras() {
		List<Cadeira> cads = new ArrayList<Cadeira>();
		cads.addAll(cadeiras);
		return cads;
	}

	public void setCadeiras(List<Cadeira> cadeiras) {
		this.cadeiras = cadeiras;
	}

	public int getNumero() {
		return numeroDoPeriodo;
	}

	public void setNumero(int numero) {
		this.numeroDoPeriodo = numero;
	}

	public List<ValidadorDePeriodo> getValidador() {
		return validadores;
	}

	public void setValidador(List<ValidadorDePeriodo> validadores) {
		this.validadores = validadores;
	}

	public void addValidador(ValidadorDePeriodo validador) {
		this.validadores.add(validador);
	}

	public List<Cadeira> getListaCadeiras() {
		return cadeiras;
	}

	public Cadeira getCadeira(String cadeira) {
		for (Cadeira c : cadeiras) {
			if (c.getNome().equals(cadeira)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Periodo [id=" + id + ", numero=" + numeroDoPeriodo
				+ ", cadeiras=" + cadeiras + "]";
	}

}