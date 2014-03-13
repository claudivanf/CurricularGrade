package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.LimitExceededException;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import models.validators.ValidadorDePeriodo;
import models.validators.ValidadorMax;
import models.validators.ValidadorMin;
import play.db.ebean.Model;

/**
 * Entidade que representa o Plano de Curso do sistema. Periodos e disciplinas
 * alocadas pelos usuarios.
 */
@Entity
public class PlanoDeCurso extends Model {

	private static final long serialVersionUID = 1L;
	private static final int QUANTIDADE_PERIODOS = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private final List<Periodo> periodos;
	@OneToOne
	private final Grade grade;
	private int periodoAtual;

	public static Finder<Long, PlanoDeCurso> find = new Finder<Long, PlanoDeCurso>(
			Long.class, PlanoDeCurso.class);

	public PlanoDeCurso() throws IOException {
		periodos = new ArrayList<Periodo>();
		grade = new Grade();
		periodoAtual = 1;

	}

	public Long getId() {
		return id;
	}

	public static void create(PlanoDeCurso p) {
		p.save();
	}

	public void setPeriodoAtual(int periodoAtual) {
		for (int i = 1; i <= 10; i++) {
			if (i < periodoAtual) {
				getPeriodo(i).addValidador(new ValidadorMax());
			} else if (i != 10) {
				getPeriodo(i).addValidador(new ValidadorMax());
				getPeriodo(i).addValidador(new ValidadorMin());
			}
		}
		this.periodoAtual = periodoAtual;
	}

	/**
	 * Distribui as cadeiras em seus respectivos períodos.
	 */
	private void distribuiCadeiras() {
		for (int idxPeriodo = 1; idxPeriodo <= QUANTIDADE_PERIODOS; idxPeriodo++) {
			List<Cadeira> disciplinas = grade
					.getCadeirasPorPeriodoPlanejado(idxPeriodo);
			periodos.add(new Periodo(idxPeriodo, disciplinas));
		}
	}

	/**
	 * Retorna o período passado como argumento.
	 * 
	 * @param numPeriodo
	 *            número relativo ao periodo 1,2,3...
	 */
	public Periodo getPeriodo(int numPeriodo) {
		return this.periodos.get(numPeriodo - 1);
	}

	public List<Periodo> getPeriodos() {
		return Collections.unmodifiableList(periodos);
	}

	/**
	 * Retorna List de cadeiras já alocadas no plano de curso.
	 */
	public List<Cadeira> getCadeirasAlocadas() {
		List<Cadeira> alocadas = new ArrayList<Cadeira>();
		for (Periodo periodo : periodos) {
			alocadas.addAll(periodo.getCadeiras());
		}
		return alocadas;
	}

	/**
	 * Retorna lista com cadeiras ainda não alocadas no plano de curso.
	 */
	public List<Cadeira> getCadeirasDisponiveis() {
		List<Cadeira> alocadas = getCadeirasAlocadas();
		List<Cadeira> disponiveis = grade.getTodasCadeirasDoCurso();
		disponiveis.removeAll(alocadas);

		return disponiveis;
	}

	/**
	 * Retorna lista com cadeira disponíveis para alocação ordenadas em ordem
	 * alfabética.
	 */
	public List<Cadeira> getCadeiraDisponiveisOrdenadas() {
		List<Cadeira> cadeirasOrdenadas = getCadeirasDisponiveis();
		Collections.sort(cadeirasOrdenadas);
		return cadeirasOrdenadas;
	}

	/**
	 * Adiciona uma {@code cadeira} ao {@code periodo}
	 * 
	 * @throws LimiteUltrapassadoException
	 * 
	 * @throws Exception
	 */
	public void addCadeira(String cadeiraNome, int idxPeriodo)
			throws LimitExceededException {

		Cadeira cadeira = grade.getCadeiraPorNome(cadeiraNome);
		int novaQtdCreditos = getPeriodo(idxPeriodo).getCreditos()
				+ cadeira.getCreditos();

		for (ValidadorDePeriodo validador : getPeriodo(idxPeriodo)
				.getValidador()) {
			if (!validador.valida(novaQtdCreditos)) {
				throw new LimitExceededException("Limite de creditos excedido");
			}
		}
		// Remove cadeira periodo anterior
		this.removeCadeira(cadeiraNome);

		// adiciona essa cadeira no periodo escolhido
		getPeriodo(idxPeriodo).addCadeira(cadeira);
	}

	/**
	 * Verifica se a cadeira tem pre-requisitos alocados erradamente (periodos
	 * posteriores ou iguais ao da cadeira).
	 * 
	 * @param cadeira
	 *            a ser verificada
	 */
	public boolean verificaRequisitos(String nomeCadeira) {
		Cadeira cadeiraVerificada = grade.getCadeiraPorNome(nomeCadeira);
		boolean requisitosAlocadosErrados = temRequisito(cadeiraVerificada);
		// chamada recursiva nos requisitos da cadeira (param)
		if (!requisitosAlocadosErrados) {
			for (Cadeira cadeira : cadeiraVerificada.getRequisitos()) {
				requisitosAlocadosErrados &= temRequisito(cadeira);
			}
		}

		return requisitosAlocadosErrados;
	}

	private boolean temRequisito(Cadeira cadeiraVerificada) {
		List<Cadeira> listaDeRequisitos = cadeiraVerificada.getRequisitos();
		int periodoCadeira = getIdxPeriodoCadeira(cadeiraVerificada);

		boolean temRequisitos = false;
		for (int idxPeriodo = periodoCadeira; idxPeriodo < periodos.size(); idxPeriodo++) {
			Periodo periodo = periodos.get(idxPeriodo);
			List<Cadeira> alocadas = periodo.getCadeiras();
			for (Cadeira cadeira : alocadas) {
				if (listaDeRequisitos.contains(cadeira)) {
					temRequisitos = true;
					break;
				}
			}
			// Parada antecipada (otimizacao)
			if (temRequisitos)
				break;
		}

		return temRequisitos;

	}

	private int getIdxPeriodoCadeira(Cadeira cadeiraProcurada) {
		int periodoCadeira = 0;
		Periodo periodoNoIdx;
		for (int idxPeriodo = 0; idxPeriodo < periodos.size(); idxPeriodo++) {
			periodoNoIdx = periodos.get(idxPeriodo);
			if (periodoNoIdx.getCadeiras().contains(cadeiraProcurada)) {
				periodoCadeira = idxPeriodo;
				break;
			}
		}
		return periodoCadeira;
	}

	/**
	 * Retorna true caso a {@code cadeira} seja pre-requisito de alguma outra
	 * nos {@code periodos}.
	 */
	public boolean isRequisito(String nomeCadeira) {
		Cadeira cadeira = grade.getCadeiraPorNome(nomeCadeira);
		// procura pela cadeira entre os periodos.
		boolean isRequisito = false;
		for (Periodo periodo : periodos) {
			for (Cadeira cadeiraDoPeriodo : periodo.getCadeiras()) {
				if (cadeiraDoPeriodo.getRequisitos().contains(cadeira)) {
					isRequisito = true;
				}
			}
		}
		return isRequisito;
	}

	public void removeCadeira(String nomeCadeira) {
		Cadeira removida = grade.getCadeiraPorNome(nomeCadeira);
		// Busca e remove cadeira (param)
		for (Periodo periodo : periodos) {
			if (periodo.getCadeiras().contains(removida)) {
				periodo.removerCadeira(removida);
			}
		}
		// remove requisitos de cadeira (param).
		List<Cadeira> requisitos = removida.getRequisitos();
		for (Periodo periodo : periodos) {
			for (Cadeira cadeira : periodo.getCadeiras()) {
				if (requisitos.contains(cadeira)) {
					removeCadeira(cadeira.getNome());
				}
			}
		}
	}
}