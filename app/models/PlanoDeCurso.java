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

import exceptions.LimiteDeCreditosException;
import exceptions.PeriodoCursandoException;

import play.db.ebean.Model;
import validators.ValidadorMax;
import validators.ValidadorMin;

/**
 * Entidade que representa o Plano de Curso do sistema.
 */
@Entity
public class PlanoDeCurso extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "plano_periodo", joinColumns = { @JoinColumn(name = "fk_plano") }, inverseJoinColumns = { @JoinColumn(name = "fk_periodo") })
	private final List<Periodo> periodos;

	private Map<String, Cadeira> mapaDeCadeiras;
	private int periodoAtual;
	private String grade;
	
	public PlanoDeCurso() {
		this.periodos = new ArrayList<Periodo>();
		for (int i = 1; i <= 10; i++) {
			periodos.add(new Periodo());
		}
		this.mapaDeCadeiras = new HashMap<String, Cadeira>();
	}
	
	public String getGrade(){
		return grade;
	}
	
	public void setGrade(String grade){
		this.grade = grade;
	}
	public static Finder<Long, PlanoDeCurso> find = new Finder<Long, PlanoDeCurso>(
			Long.class, PlanoDeCurso.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean setPeriodoCursando(int periodoCursando)
			throws PeriodoCursandoException {
		if (periodoCursando < 1 || periodoCursando > 10) {
			throw new PeriodoCursandoException("Periodo Inválido!");
		}
		//verifica se o periodo a ser setado atual esta com limite minimo insuficiente.
		if (getPeriodo(periodoCursando).getCreditos() < 12){
			throw new PeriodoCursandoException(
					"Esse período não pode ser o atual pois tem o limite mínimo de créditos insuficiente!");
		}
		for (int i = 1; i <= 10; i++) {
			Periodo p = getPeriodo(i);
			p.clearValidadores();
			if (i < periodoCursando) {
				p.addValidador(new ValidadorMax());
			} else if (i == periodoCursando) {
				p.addValidador(new ValidadorMax());
				p.addValidador(new ValidadorMin());
			} else if (i != 10) {
				p.addValidador(new ValidadorMax());
			}
		}
		this.periodoAtual = periodoCursando;
		return true;
	}

	/**
	 * Distribui as cadeiras em seus respectivos períodos.
	 * 
	 * @throws LimiteDeCreditosException
	 */
	private void distribuiCadeiras() throws LimiteDeCreditosException {
		for (Cadeira c : mapaDeCadeiras.values()) {
			Periodo p = getPeriodo(c.getPeriodoOriginal());
			p.addCadeira(c);
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
		return periodoAtual;
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
	public List<Cadeira> getCadeirasDisponiveisOrdenadas() {
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
			for (Cadeira c: p.getCadeiras()){
				if (c.getNome().equals(cadeira.getNome())){
					periodoAtualDaCadeira = p;
				}
			}
		}

		// adiciona essa cadeira no periodo escolhido
		periodoDestinoDaCadeira.addCadeira(cadeira);
		
		// remove cadeira do periodo
		if(periodoAtualDaCadeira != null){
			periodoAtualDaCadeira.removerCadeira(cadeira);
		}
	}

	/**
	 * Varifica se a cadeira tem pre-requisitos alocados erradamente.
	 * 
	 * @param cadeira
	 *            a ser verificada
	 */
	public boolean verificaPrerequisito(String cadeira) {
		Cadeira cad = mapaDeCadeiras.get(cadeira); // cadeira a ser verificada
		int periodo_cad = 1; // periodo da cadeira a ser verificada
		int index = 1;
		for (Periodo p : periodos) {
			if (p.getCadeiras().contains(cad)){
				periodo_cad = index;
			}
			index++;
		}
		//verifica se pelo menos um de seus pre-requisitos não esta alocado no plano
		for (Cadeira requisito: cad.getRequisitos()){
			for (Cadeira c: getCadeirasDisponiveisOrdenadas()){
				if (c.getNome().equals(requisito.getNome()))
					return true;
			}
		}

		int periodo_req = 1;
		for (Periodo p : periodos) {
			for (Cadeira c : p.getCadeiras()) {
				if (cad.isPreRequisito(c) && periodo_req >= periodo_cad) {
					return true;
				}
			}
			periodo_req += 1;
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

	public void removeCadeira(String cadeira) throws LimiteDeCreditosException {
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

	public void atualizaValidadoresPeriodos() throws PeriodoCursandoException {
		setPeriodoCursando(periodoAtual);
	}

	public Map<String, Cadeira> getMapaDeCadeiras() {
		return mapaDeCadeiras; 
	}
	
	public int getCreditosPagos(){
		int creditosPagos = 0;
		for (int i = 0; i < periodoAtual - 1; i++) {
			creditosPagos += periodos.get(i).getCreditos();
		}
		return creditosPagos;
	}
	
	public int getCreditosAtuais(){
		return periodos.get(periodoAtual - 1).getCreditos();
	}
	
	public int getCreditosFuturos(){
		int creditosFuturos = 0;
		for (int i = periodos.size()-1; i > periodoAtual - 1; i--) {
			creditosFuturos += periodos.get(i).getCreditos();
		}
		return creditosFuturos;
	}
	
	public int getCreditosRestantes(){
		return 208 - getCreditosPagos();
	}
}