package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import managers.GerenciadorDeCadeiras;
import play.db.ebean.Model;

/**
 * Entidade que representa o Plano de Curso do sistema.
 */
@Entity
public class PlanoDeCurso extends Model{

	// TODO PADRÃO DE PROJETO: ALTA COESÃO - so haverá informações coerentes com
	// a classe
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	private List<Periodo> periodos;
	
	
	//@OneToOne
	private Usuario usuario;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable
	private Map<String, Cadeira> mapaDeCadeiras;
	
	public static final int PRIMEIRO_PERIODO = 1;
	public static final int MAXIMO_CREDITOS = 28;

	public PlanoDeCurso() {
		// TODO Responsabilidade Atribuita seguindo o padrão Creator
		// O plano de curso ficou responsável por criar os períodos.
		this.periodos = new ArrayList<Periodo>();
		for (int i = 1; i<= 10; i++ ){
			periodos.add(new Periodo(i));
		}
		// seta o mapa de cadeiras com as cadeiras do xml
		this.mapaDeCadeiras = GerenciadorDeCadeiras.getMapaDeCadeiras();
		
		//irá distribuir as cadeiras entre os periodos
		distribuiCadeiras();  
	}

	public static Finder<Long,PlanoDeCurso> find = new Finder<Long,PlanoDeCurso>(
		    Long.class, PlanoDeCurso.class
	);
	
	public static void create(PlanoDeCurso p) {
		p.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}
	
	public static void atualizar(Long id) {
		PlanoDeCurso p = find.ref(id);
		p.update();
	}
	
	/**
	 * Distribui as cadeiras recém-retiradas do xml e adiciona em seus
	 * respectivos períodos
	 */
	public void distribuiCadeiras(){
		for(Cadeira c: mapaDeCadeiras.values()){
			Periodo p = getPeriodo(c.getPeriodo());
			p.addCadeira(c);
			c.setPlano(this);
		}
	}
	
	/**
	 * Adiciona um periodo à lista de períodos, de acordo com o tamanho da
	 * lista.
	 * 
	 * Seguindo o padrão creator.
	 */
	public void addPeriodo() {
		this.periodos.add(new Periodo(this.periodos.size() + 1));
	}
	
	public void addPeriodo(int num_periodo) {
		this.periodos.add(new Periodo(num_periodo));
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Cadeira getCadeira(String cadeira){
		for (Periodo p: periodos){
			if (p.getCadeira(cadeira) != null){
				return p.getCadeira(cadeira);
			}
		}
		return null;
	}

	public void setMapaDeCadeiras(Map<String, Cadeira> mapa){
		this.mapaDeCadeiras = mapa;
	}
	
	public Long getId(){
		return id;
	}
	
	/**
	 * Retorna o Map de cadeiras já alocadas no plano de curso.
	 */
	public Map<String, Cadeira> getMapCadeirasAlocadas() {
		Map<String, Cadeira> alocadas = new HashMap<String, Cadeira>();
		for (Periodo periodo : periodos) {
			alocadas.putAll(periodo.getCadeiras());
		}
		return alocadas;
	}

	/**
	 * Retorna o Map de cadeiras ainda não alocadas no plano de curso.
	 */
	public Map<String, Cadeira> getMapCadeirasDisponiveis() {
		Map<String, Cadeira> disponiveis = new HashMap<String, Cadeira>();
		Map<String, Cadeira> alocadas = getMapCadeirasAlocadas();
		for (String nomeCadeira : mapaDeCadeiras.keySet()) {
			if (!alocadas.containsKey(nomeCadeira)) {
				disponiveis.put(nomeCadeira, mapaDeCadeiras.get(nomeCadeira));
			}
		}
		return disponiveis;
	}
	
	/**
	 * Retorna a lista de cadeira disponíveis para alocação ordenadas em ordem
	 * alfabética.
	 */
	public List<Cadeira> getCadeiraDispniveisOrdenadas(){
		List<Cadeira> cadeirasOrdenadas = new ArrayList<Cadeira>();
		cadeirasOrdenadas.addAll(getMapCadeirasDisponiveis().values());
		Collections.sort(cadeirasOrdenadas);
		return cadeirasOrdenadas;
	}

	/**
	 * Adiciona uma {@code cadeira} ao {@code periodo}
	 * 
	 * @throws Exception
	 */
	public void addCadeira(String cadeiraNome, int periodo) throws Exception {
		// TODO PADRÃO DE PROJETO: CONTROLLER - para manter o baixo acoplamento
		// essa classe vai ser a responsável por adicionar um cadeira ao periodo
		Cadeira cadeira = getCadeira(cadeiraNome);
		//if (getPeriodo(periodo).getCreditos() + cadeira.getCreditos() > MAXIMO_CREDITOS) {
		//	throw new NotSupportedException("limite de créditos ultrapassado!");
		//}
		//verificaPreRequisitos(cadeira, periodo);
		
		//remove cadeira do periodo
		getPeriodo(cadeira.getPeriodo()).removerCadeira(cadeira);
		// adiciona essa cadeira no periodo escolhido
		getPeriodo(periodo).addCadeira(cadeira);
		//seta o apontador do periodo para o periodo escolhido
		cadeira.setPeriodo(periodo); 
	}
	
	/**
	 * Varifica se a cadeira tem pre-requisitos alocados erradamente.
	 * 
	 * @param cadeira a ser verificada
	 */
	public boolean verificaPrerequisito(String cadeira){
		Cadeira cad = getCadeira(cadeira);
		for (Periodo p: periodos){
			for (Cadeira c: p.getListaCadeiras()){
				if (cad.isPreRequisito(c) && c.getPeriodo() >= cad.getPeriodo()){
					return true;
				}
			}
		}
		// verifica também recursivamente em seus pre-requisitos
		for(Cadeira c: cad.getPreRequisitos()){
			if(verificaPrerequisito(c.getNome())){
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
			for (Cadeira cadeiraDoPeriodo : periodo.getListaCadeiras()) {
				if (cadeiraDoPeriodo.getPreRequisitos().contains(cadeira)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Remove o período e todos os posteriores
	 */
	public void removePeriodo(int periodo) {
		this.periodos = periodos.subList(0, periodo - 1);
	}

	public void removeCadeira(String cadeira) throws Exception {
		// TODO PADRÃO DE PROJETO: CONTROLLER - para manter o baixo acoplamento
		// essa classe vai ser a responsável por remover uma cadeira ao periodo
		if (getMapCadeirasAlocadas().get(cadeira) == null) {
			throw new Exception("Essa Cadeira não está alocada!");
		}
		Cadeira removida = getMapCadeirasAlocadas().get(cadeira);
		// procura pela cadeira entre os periodos.
		for (Periodo periodo : periodos) {
			// remove a cadeira
			if (periodo.getCadeiras().get(cadeira) != null) {
				periodo.removerCadeira(removida);
			}
			// verifica as cadeiras que tem a cadeira a ser removida como
			// pre-requisito e remove
			for (Cadeira c : periodo.getListaCadeiras()) {
				if (c.getPreRequisitos().contains(removida)) {
					removeCadeira(c.getNome());
				}
			}
		}
	}
}