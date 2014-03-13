package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

import com.google.common.base.Objects;

/**
 * Entidade que representa uma Cadeira.
 */
@Entity
public class Cadeira extends Model implements Comparable<Cadeira> {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	private String nome;
	private int creditos;
	private int periodoEsperado;
	@ManyToMany
	@JoinTable(name = "cadeira_dependentes", joinColumns = @JoinColumn(name = "cadeira_codigo", referencedColumnName = "codigo"), inverseJoinColumns = @JoinColumn(name = "dependente_id", referencedColumnName = "codigo"))
	private List<Cadeira> dependentes;
	@ManyToMany
	@JoinTable(name = "disciplinas_requisitos", joinColumns = @JoinColumn(name = "disciplina_codigo", referencedColumnName = "codigo"), inverseJoinColumns = @JoinColumn(name = "requisito_codigo", referencedColumnName = "codigo"))
	private final List<Cadeira> requisitos;
	@ManyToMany
	private int dificuldade; // dificuldade de 1 - 10

	public static Finder<Long, Cadeira> find = new Finder<Long, Cadeira>(
			Long.class, Cadeira.class);

	public Cadeira() {
		this.requisitos = new ArrayList<Cadeira>();
		this.dependentes = new ArrayList<Cadeira>();
	}

	public Cadeira(Long id, String nome, int creditos, int periodoEsperado,
			int dificuldade) {
		this(); // chamada ao construtor padrao.
		this.id = id;
		this.nome = nome;
		this.creditos = creditos;
		this.periodoEsperado = periodoEsperado;
		setDificuldade(dificuldade);
	}

	/**
	 * Retorna verdadeiro caso a cadeira {@code cadeira} seja pre-requisito
	 * 
	 */
	public boolean isDependente(Cadeira cadeira) {
		return this.getDependentes().contains(cadeira);
	}

	public void addDependente(Cadeira cadeira) {
		this.dependentes.add(cadeira);
	}

	public Long getID() {
		return this.id;
	}

	public int getCreditos() {
		return this.creditos;
	}

	public int getPeriodoPlanejado() {
		return this.periodoEsperado;
	}

	public String getNome() {
		return this.nome;
	}

	public int getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(int dificuldade) {
		this.dificuldade = dificuldade;
	}

	public List<Cadeira> getRequisitos() {
		return Collections.unmodifiableList(this.requisitos);
	}

	public List<Cadeira> getDependentes() {
		return Collections.unmodifiableList(this.dependentes);
	}

	public void setDependentes(List<Cadeira> dependentes) {
		this.dependentes = dependentes;
	}

	public static void create(Cadeira c) {
		c.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	public static void atualizar(Long id) {
		Cadeira p = find.ref(id);
		p.update();
	}

	public int compareTo(Cadeira cadeira) {
		return getNome().compareTo(cadeira.getNome());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getNome(), creditos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cadeira other = (Cadeira) obj;
		return Objects.equal(this.getCreditos(), other.getCreditos())
				&& Objects.equal(this.getNome(), other.getNome());
	}

	@Override
	public String toString() {
		return "Cadeira [id=" + id + ", nome=" + nome + "]";
	}

}