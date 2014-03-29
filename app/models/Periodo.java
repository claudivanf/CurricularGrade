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

import exceptions.LimiteDeCreditosException;

import play.db.ebean.Model;
import validators.ValidadorDePeriodo;

/**
 * Entidade que representa um período
 */
@Entity
public class Periodo extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "periodo_cadeira", joinColumns = { @JoinColumn(name = "fk_periodo") }, inverseJoinColumns = { @JoinColumn(name = "fk_cadeira") })
	private List<Cadeira> cadeiras;

	private List<ValidadorDePeriodo> validadores;

	public Periodo() {
		cadeiras = new ArrayList<Cadeira>();
		validadores = new ArrayList<ValidadorDePeriodo>();
	}

	public Long getId() {
		return id;
	}

	public static Finder<Long, Periodo> find = new Finder<Long, Periodo>(
			Long.class, Periodo.class);

	public void addCadeira(Cadeira cadeira) throws LimiteDeCreditosException {
		int novaQuantidadeCreditos = this.getCreditos() + cadeira.getCreditos();
		boolean novaQuantidadeCreditosIsValido = valida(novaQuantidadeCreditos);

		if (novaQuantidadeCreditosIsValido) {
			cadeiras.add(cadeira);
		}
	}

	private boolean valida(int novaQuantidadeCreditos)
			throws LimiteDeCreditosException {
		boolean isValido = true;
		for (ValidadorDePeriodo validador : validadores) {
			isValido = isValido && validador.valida(novaQuantidadeCreditos);
		}

		return isValido;
	}

	public boolean removerCadeira(Cadeira cadeira)
			throws LimiteDeCreditosException {
		int novaQuantidadeCreditos = this.getCreditos() - cadeira.getCreditos();
		boolean novaQuantidadeCreditosIsValido = valida(novaQuantidadeCreditos);

		if (novaQuantidadeCreditosIsValido) {
			return cadeiras.remove(cadeira);
		}
		return false;
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

	public void clearValidadores() {
		validadores.clear();
	}

	public void setCadeiras(List<Cadeira> cadeiras) {
		this.cadeiras = cadeiras;
	}

	public List<ValidadorDePeriodo> getValidadores() {
		return validadores;
	}

	public void setValidadores(List<ValidadorDePeriodo> validadores) {
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
}