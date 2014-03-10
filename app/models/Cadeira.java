package models;

import java.util.ArrayList;
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

	// TODO PADRÃO DE PROJETO: ALTA COESÃO - so haverá informações coerentes com
	// a classe

	@Id
	public Long id;
	private String nome;
	private int creditos;
	// TODO changed BD structure
	@ManyToMany
	@JoinTable(name = "cadeira_requisito", joinColumns = @JoinColumn(name = "id_disciplina"), inverseJoinColumns = @JoinColumn(name = "id_requisito"))
	private List<Cadeira> requisitos;
	
	private int dificuldade; // dificuldade de 1 - 10
	private int periodoOriginal; //Periodo original da disciplina segundo a grade curricular

	public Cadeira() {
		setRequisitos(new ArrayList<Cadeira>());
	}

	public Cadeira(String nome, int dificuldade) {
		this.setNome(nome);
		this.creditos = 4;
		this.dificuldade = dificuldade;
		setRequisitos(new ArrayList<Cadeira>());
	}

	public Cadeira(String nome, int dificuldade, int creditos) {
		this(nome, dificuldade);
		this.creditos = creditos;
	}

	/**
	 * Retorna verdadeiro caso a cadeira {@code c} seja pre-requisito, Seguindo
	 * o padrão Information Expert, quem deve saber se uma cadeira é
	 * pre-requisito é a mesma.
	 */
	public boolean isPreRequisito(Cadeira c) {
		return this.getRequisitos().contains(c);
	}

	// TODO PADRÃO DE PROJETO: INFORMATION EXPERT - a classe cadeira é a
	// responsável por guardar e adicionar pre-requisitos
	public void addDependentes(Cadeira... c) {
		Cadeira[] lista = c;
		for (Cadeira cadeira : lista) {
			getRequisitos().add(cadeira);
		}
	}

	public int getCreditos() {
		return this.creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(int dificuldade) {
		this.dificuldade = dificuldade;
	}

	public List<Cadeira> getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(List<Cadeira> preRequisitos) {
		this.requisitos = preRequisitos;
	}
	
	public int getPeriodoOriginal(){
 		return periodoOriginal;
 	}
 	
 	public void setPeriodoOriginal(int periodo){
 		this.periodoOriginal = periodo;
 	}
	// TODO Removed get and set Periodo, Cadeira Shouldn't know in what periodo
	// it is

	public static Finder<Long, Cadeira> find = new Finder<Long, Cadeira>(
			Long.class, Cadeira.class);

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