package models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CarregadorDeCadeiras {

	private static Map<String, Cadeira> listaDeCadeiras = new HashMap<String, Cadeira>();

	private static void populaMapas() {
		Map<String, Cadeira> cadeirasPorId = new HashMap<String, Cadeira>();
		try {
			Document doc = criaParserXml();
			NodeList nList = doc.getElementsByTagName("cadeira");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					criaCadeiras(cadeirasPorId, nNode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria a cadeira e insere no mapa de acordo com seus atributos descritos no
	 * xml de {@code nNode}.
	 */
	private static void criaCadeiras(Node nNode) {
		Element cadeiraXml = (Element) nNode;

		long idCadeira = Long.parseLong(cadeiraXml.getAttribute("id"));
		String nomeCadeira = cadeiraXml.getAttribute("nome");
		int creditos = Integer.parseInt(cadeiraXml
				.getElementsByTagName("creditos").item(0).getTextContent());
		int periodo = Integer.parseInt(cadeiraXml
				.getElementsByTagName("periodo").item(0).getTextContent());
		int dificuldade = Integer.parseInt(cadeiraXml
				.getElementsByTagName("dificuldade").item(0).getTextContent());

		Cadeira novaCadeira = new Cadeira(idCadeira, nomeCadeira, creditos,
				periodo, dificuldade);

		NodeList requisitos = cadeiraXml.getElementsByTagName("id");
		for (int i = 0; i < requisitos.getLength(); i++) {
			novaCadeira.addDependentes(cadeirasPorId.get(requisitos.item(i)
					.getTextContent()));
		}

		listaDeCadeiras.put(novaCadeira.getNome(), novaCadeira);

	}

	/**
	 * Cria um parser XML
	 */
	private static Document criaParserXml()
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File("test/MOCK/cadeiras.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		return doc;
	}

	public List<Cadeira> getListaDeCadeiras() {
		if (listaDeCadeiras.isEmpty()) {
			populaMapas();
		}
		List<Cadeira> lista = new ArrayList<Cadeira>();
		for (Cadeira c : listaDeCadeiras.values()) {
			lista.add(c);
		}
		return lista;
	}
}