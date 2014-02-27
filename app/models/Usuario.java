package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.google.common.base.Objects;

import play.db.ebean.Model;

@Entity
public class Usuario extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	private String nome;
	private String senha;
	
	@OneToOne(cascade=CascadeType.ALL)
	@Column(name="fk_plano")
	private PlanoDeCurso plano;

	public Usuario(String nome, String senha) {
		this.nome = nome;
		this.senha = senha;
		plano = new PlanoDeCurso();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public PlanoDeCurso getPlano() {
		return plano;
	}

	public void setPlano(PlanoDeCurso plano) {
		this.plano = plano;
	}

	public static Finder<Long, Usuario> find = new Finder<Long, Usuario>(
			Long.class, Usuario.class);

	public static void create(Usuario u) {
		u.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	public static void atualizar(Long id) {
		Usuario p = find.ref(id);
		p.update();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.nome, this.senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equal(this.nome, other.getNome()) &&
				Objects.equal(this.senha, other.getSenha());
	}

}
