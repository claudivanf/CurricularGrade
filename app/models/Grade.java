package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import play.db.ebean.Model;

/**
 * Armazena todas as cadeirasDoCurso do sistema.
 */
@Entity
public class Grade extends Model {

	private static final long serialVersionUID = -809721958825402175L;

	@Id
	public Long id;
	@ManyToMany
	private final List<Cadeira> cadeirasDoCurso;

	/**
	 * Construtor
	 * 
	 * @throws IOException
	 *             Erro na leitura do arquivo.
	 */
	public Grade() throws IOException {
		cadeirasDoCurso = new ArrayList<Cadeira>();
	}

	/**
	 * Preenche a grade com cadeirasDoCurso.
	 * 
	 * @throws IOException
	 *             Erro na leitura do arquivo.
	 * @throws ParserConfigurationException
	 *             Erro na configuração do parser XML
	 * @throws SAXException
	 *             Erro no arquivo XML
	 */
	public void preencheGrade() throws IOException,
			ParserConfigurationException, SAXException {
		CarregadorDeCadeiras carregador = new CarregadorDeCadeiras();

		List<Cadeira> cadeirasDoCurso = carregador.getListaDeCadeiras();

		for (Cadeira cadeira : cadeirasDoCurso) {
			cadeira.save();
		}

		for (Cadeira cadeira : cadeirasDoCurso) {
			cadeira.update();
		}

		this.cadeirasDoCurso = cadeirasDoCurso;
	}

	/**
	 * Busca e retorna por uma cadeira pelo nome.
	 * 
	 * @param nomecadeiraProcurada
	 *            Nome da cadeira procurada.
	 * @return cadeira procurada ou Null em caso de mesma não existir.
	 */
	public Cadeira getCadeiraPorNome(String nomecadeiraProcurada) {
		Cadeira encontrada = null;

		for (Cadeira cadeira : cadeirasDoCurso) {
			if (cadeira.getNome().equals(nomecadeiraProcurada)) {
				encontrada = cadeira;
				break;
			}
		}

		return encontrada;
	}

	/**
	 * Busca e retorna por uma cadeira pelo ID.
	 * 
	 * @param nome
	 *            ID da cadeira procurada.
	 * @return cadeira procurada ou Null caso a mesma não exista.
	 */
	public Cadeira getCadeiraPorID(Long IDBuscado) {
		Cadeira encontrada = null;

		for (Cadeira cadeira : cadeirasDoCurso) {
			if (cadeira.getID().equals(IDBuscado)) {
				encontrada = cadeira;
				break;
			}
		}

		return encontrada;
	}

	/**
	 * Retorna todas as cadeirasDoCurso da grade.
	 * 
	 * @return Conjunto de cadeirasDoCurso.
	 */
	public List<Cadeira> getTodasCadeirasDoCurso() {
		return this.cadeirasDoCurso;
	}

	/**
	 * Retorna lista com todas as cadeiras com periodo planejado igual ao
	 * {@code idxPeriodo}, ou um ArrayList vazio caso nao possua cadeiras que
	 * satisfazem condicao.
	 * 
	 * @param idxPeriodo
	 * @return conjunto de cadeiras que satisfazem o {@code idxPeriodo}
	 */
	public List<Cadeira> getCadeirasPorPeriodoPlanejado(int idxPeriodo) {
		List<Cadeira> encontradas = new ArrayList<Cadeira>();

		for (Cadeira cadeira : cadeirasDoCurso) {
			if (cadeira.getPeriodoPlanejado() == idxPeriodo) {
				encontradas.add(cadeira);
			}
		}

		return encontradas;
	}
}