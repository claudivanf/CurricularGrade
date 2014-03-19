package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import models.exceptions.LimiteDeCreditosException;
import models.validators.ValidadorDePeriodo;
import models.validators.ValidadorMax;
import models.validators.ValidadorMin;
import play.db.ebean.Model;

/**
 * Entidade que representa o Plano de Curso do sistema.
 */
@Entity
public class PlanoDeCurso extends Model {

	// TODO PADRÃO DE PROJETO: ALTA COESÃO - so haverá informações coerentes com
	// a classe

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "plano_periodo", joinColumns = { @JoinColumn(name = "fk_plano") }, inverseJoinColumns = { @JoinColumn(name = "fk_periodo") })
	private final List<Periodo> periodos;

	private Map<String, Cadeira> mapaDeCadeiras;
	private int periodoCursando;

	public PlanoDeCurso() {
		// TODO Responsabilidade Atribuita seguindo o padrão Creator
		// O plano de curso ficou responsável por criar os períodos.
		this.periodos = new ArrayList<Periodo>();
		for (int i = 1; i <= 10; i++) {
			periodos.add(new Periodo(i));
		}
		this.mapaDeCadeiras = new HashMap<String, Cadeira>();
	}

	public static Finder<Long, PlanoDeCurso> find = new Finder<Long, PlanoDeCurso>(
			Long.class, PlanoDeCurso.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static void create(PlanoDeCurso p) {
		p.save();
	}

	public boolean setPeriodoCursando(int periodoCursando) {
		if (periodoCursando < 1 || periodoCursando > 10) {
			return false;
		}
		for (int i = 1; i <= 10; i++) {
			if (i < periodoCursando) {
				getPeriodo(i).addValidador(new ValidadorMax());
			} else if (i == periodoCursando) {
				getPeriodo(i).addValidador(new ValidadorMax());
				getPeriodo(i).addValidador(new ValidadorMin());
			} else if (i != 10) {
				getPeriodo(i).addValidador(new ValidadorMax());
			}
		}
		this.periodoCursando = periodoCursando;
		return true;
	}

	/**
	 * Distribui as cadeiras em seus respectivos períodos.
	 * 
	 * @throws LimiteDeCreditosException
	 */
	private void distribuiCadeiras() throws LimiteDeCreditosException {
		for (Cadeira c : mapaDeCadeiras.values()) {
			if (c.getPeriodoOriginal() != 0) {
				Periodo p = getPeriodo(c.getPeriodoOriginal());
				p.addCadeira(c);
			}
		}
	}

	/**
	 * Distribui Cadeiras entre os periodos quando o plano é iniciado pela
	 * primeira vez.
	 * 
	 * @throws LimiteDeCreditosException
	 */
	public void distribuiCaderas(List<Cadeira> cadeiras)
			throws LimiteDeCreditosException {
		atualizaMapaCadeira(cadeiras);
		distribuiCadeiras();
	}

	/**
	 * Atualiza o mapadecadeiras das disciplinas com base em uma lista de todas
	 * as cadeiras existentes.
	 */
	public void atualizaMapaCadeira(List<Cadeira> cadeiras) {
		this.mapaDeCadeiras = new HashMap<String, Cadeira>();
		for (Cadeira c : cadeiras) {
			mapaDeCadeiras.put(c.getNome(), c);
		}
	}

	public Map<String, Cadeira> getMapaDeCadeiras() {
		return mapaDeCadeiras;
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
		return this.periodos;
	}

	public int getPeriodoCursando() {
		return periodoCursando;
	}

	/**
	 * Retorna o Map de cadeiras já alocadas no plano de curso.
	 */
	public List<Cadeira> getCadeirasAlocadas() {
		List<Cadeira> alocadas = new ArrayList<Cadeira>();
		for (Periodo periodo : periodos) {
			alocadas.addAll(periodo.getCadeiras());
		}
		return alocadas;
	}

	/**
	 * Retorna o Map de cadeiras ainda não alocadas no plano de curso.
	 */
	public Map<String, Cadeira> getMapCadeirasDisponiveis() {
		Map<String, Cadeira> disponiveis = new HashMap<String, Cadeira>();
		List<Cadeira> alocadas = getCadeirasAlocadas();
		for (Cadeira c : mapaDeCadeiras.values()) {
			if (!alocadas.contains(c)) {
				disponiveis.put(c.getNome(), c);
			}
		}
		return disponiveis;
	}

	/**
	 * Retorna a lista de cadeira disponíveis para alocação ordenadas em ordem
	 * alfabética.
	 */
	public List<Cadeira> getCadeiraDispniveisOrdenadas() {
		List<Cadeira> cadeirasOrdenadas = new ArrayList<Cadeira>();
		cadeirasOrdenadas.addAll(getMapCadeirasDisponiveis().values());
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
	public void addCadeira(String cadeiraNome, int periodo)
			throws LimiteDeCreditosException {
		Cadeira cadeira = mapaDeCadeiras.get(cadeiraNome);
		Periodo periodoAtualDaCadeira = null;
		Periodo periodoDestinoDaCadeira = getPeriodo(periodo);

		for (Periodo p : periodos) {
			if (p.getCadeiras().contains(cadeira)) {
				periodoAtualDaCadeira = p;
			}
		}

		int qtdCreditosPeriodoAtual = getPeriodo(periodo).getCreditos()
				- cadeira.getCreditos();
		int qtdCreditosPeriodoDestino = getPeriodo(periodo).getCreditos()
				+ cadeira.getCreditos();

		for (ValidadorDePeriodo validador : periodoAtualDaCadeira
				.getValidador()) {
			validador.valida(qtdCreditosPeriodoAtual);
		}
		for (ValidadorDePeriodo validador : periodoDestinoDaCadeira
				.getValidador()) {
			validador.valida(qtdCreditosPeriodoDestino);
		}

		// remove cadeira do periodo
		periodoAtualDaCadeira.removerCadeira(cadeira);

		// adiciona essa cadeira no periodo escolhido
		periodoDestinoDaCadeira.addCadeira(cadeira);
	}

	/**
	 * Varifica se a cadeira tem pre-requisitos alocados erradamente.
	 * 
	 * @param cadeira
	 *            a ser verificada
	 */
	public boolean verificaPrerequisito(String cadeira) {
		Cadeira cad = mapaDeCadeiras.get(cadeira); // cadeira a ser verificada
		int periodo_cad = 0; // periodo da cadeira a ser verificada
		for (Periodo p : periodos) {
			if (p.getCadeiras().contains(cad)) {
				periodo_cad = p.getNumero();
			}
		}
		for (Periodo p : periodos) {
			for (Cadeira c : p.getCadeiras()) {
				if (cad.isPreRequisito(c) && p.getNumero() >= periodo_cad) {
					return true;
				}
			}
		}
		// verifica também recursivamente em seus pre-requisitos
		for (Cadeira c : cad.getRequisitos()) {
			if (verificaPrerequisito(c.getNome())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna true caso a {@code cadeira} seja pre-requisito de alguma outra
	 * nos {@code periodos}.
	 */
	public boolean isPreRequisito(String cad) {
		Cadeira cadeira = mapaDeCadeiras.get(cad);
		// procura pela cadeira entre os periodos.
		for (Periodo periodo : periodos) {
			// verifica as cadeiras que tem a cadeira a ser removida como
			// pre-requisito
			for (Cadeira cadeiraDoPeriodo : periodo.getCadeiras()) {
				if (cadeiraDoPeriodo.getRequisitos().contains(cadeira)) {
					return true;
				}
			}
		}
		return false;
	}

	public void removeCadeira(String cadeira) {
		// TODO PADRÃO DE PROJETO: CONTROLLER - para manter o baixo acoplamento
		// essa classe vai ser a responsável por remover uma cadeira ao periodo
		// if (getMapCadeirasAlocadas().get(cadeira) == null) {
		// throw new Exception("Essa Cadeira não está alocada!");
		// }
		Cadeira removida = mapaDeCadeiras.get(cadeira);
		// procura pela cadeira entre os periodos.

		for (Periodo p : periodos) {
			if (p.getCadeiras().contains(removida)) {
				p.removerCadeira(removida);
			}
		}
		for (Periodo p : periodos) {
			for (Cadeira c : p.getCadeiras()) {
				if (c.isPreRequisito(removida)) {
					removeCadeira(c.getNome());
				}
			}
		}
	}

	public void atualizaPeriodoAtual() {
		setPeriodoCursando(periodoCursando);
	}
}