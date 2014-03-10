package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.google.common.base.Objects;

@Entity
public class Usuario extends Model {

	private static final long serialVersionUID = 1L;
	// TODO User Id should be the email
	@Id
	@Required
	@Email
	private String email;
	@Required
	private String nome;
	@Required
	private String senha;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private PlanoDeCurso plano;
	private int periodoAtual;

	public static Finder<String, Usuario> find = new Finder<String, Usuario>(
			String.class, Usuario.class);

	public Usuario(String email, String nome, String senha) {
		this.email = email;
		this.nome = nome;
		this.senha = senha;
		plano = new PlanoDeCurso();
		setPeriodoAtual(1);
	}

	public int getPeriodoAtual() {
		return this.periodoAtual;
	}

	public void setPeriodoAtual(int numPeriodo) {
		this.periodoAtual = numPeriodo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// TODO criar classe de hash para senha, aumentar seguranca :p
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

	public boolean autenticar(String senha) {
		boolean autenticado = false;
		if (getSenha().equals(senha)) {
			autenticado = true;
		}
		return autenticado;
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
		return Objects.equal(this.nome, other.getNome())
				&& Objects.equal(this.senha, other.getSenha());
	}
}