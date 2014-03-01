import models.Cadeira;
import models.PlanoDeCurso;
import models.exceptions.LimiteUltrapassadoException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import MOCK.GerenciadorDeCadeiras;
/**
 * 
 * Simple (JUnit) tests that can call all parts of a play app. If you are
 * interested in mocking a whole application, see the wiki for more details.
 * 
 */
public class PlanoDeCursoTest {
	
	PlanoDeCurso plano;
	
	@Before
	public void setUp(){
		// cria e popula o plano a cada @Test
		// com os parâmetros originais da grade de CC.
		plano = new PlanoDeCurso();
		plano.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
	}

	@Test
	public void testaDadosPlano(){
		// TODO
		// testa quantidade de periodos
		// testa quantidade de cadeiras alocadas
		// testa quantidade de cadeiras disponiveis
		// testa creditos total
		
	}
	
	@Test
	public void testaCadeirasESeusRequisitos(){
		// TODO
		// testa requisitos das cadeiras
		// testa cadeiras sem requisitos
	}
	
	@Test
	public void testaListarPrimeiroPeriodo() {
		Cadeira p1 = plano.getMapaCadeira().get("Programação I");
		
		Assert.assertEquals(6, plano.getPeriodo(1).getCadeiras().size());
		Assert.assertEquals(p1, plano.getPeriodo(1).getCadeira(p1.getNome()));
		
		//TODO
		// testar mais cadeiras
		// testar creditos
	}
	
	@Test
	public void testaAdicionarCadeira() {
		Cadeira p1 = plano.getMapaCadeira().get("Programação I");

		try {
			plano.addCadeira("Programação I", 2);
		} catch (LimiteUltrapassadoException e) {
			Assert.assertEquals("Limite de Créditos Ultrapassado!", e.getMessage());
		}
		try {
			plano.addCadeira("Programação I", 10);
			Assert.assertEquals(p1, plano.getPeriodo(10).getCadeira(p1.getNome()));
		} catch (LimiteUltrapassadoException e) {
			Assert.fail("nao devia ter lançado exceptio");
		}

		Assert.assertEquals(5, plano.getPeriodo(1).getCadeiras().size());
		Assert.assertEquals(1, plano.getPeriodo(10).getCadeiras().size());
		
	}

	@Test
	public void testaCreditosDoPeriodo() throws Exception {

		Assert.assertEquals(24, plano.getPeriodo(1).getCreditos());
		Assert.assertEquals(26, plano.getPeriodo(2).getCreditos());
		Assert.assertEquals(28, plano.getPeriodo(3).getCreditos());
		Assert.assertEquals(26, plano.getPeriodo(4).getCreditos());
		Assert.assertEquals(24, plano.getPeriodo(5).getCreditos());
		Assert.assertEquals(28, plano.getPeriodo(6).getCreditos());
		Assert.assertEquals(28, plano.getPeriodo(7).getCreditos());
		Assert.assertEquals(24, plano.getPeriodo(8).getCreditos());
		Assert.assertEquals(0, plano.getPeriodo(9).getCreditos());
		Assert.assertEquals(0, plano.getPeriodo(10).getCreditos());

		Cadeira p1 = plano.getMapaCadeira().get("Programação I");

		try {
			plano.addCadeira("Programação I", 10);
			Assert.assertEquals(p1, plano.getPeriodo(10).getCadeira(p1.getNome()));
		} catch (LimiteUltrapassadoException e) {
			Assert.fail("nao devia ter lançado exceptio");
		}
		
		Assert.assertEquals(20, plano.getPeriodo(1).getCreditos());
		Assert.assertEquals(4, plano.getPeriodo(10).getCreditos());
		
	}

	@Test
	public void testaUltrapassarLimiteDeCreditos() throws Exception {
		// TODO
		//testar ultrapassar o limite
	}

	@Test
	public void testaDificuldade() {
		Assert.assertEquals(37, plano.getPeriodo(2).getDificuldadeTotal());
		//TODO
		// testar os outros periodos
	}

	@Test
	public void testaAddCadeiraComPreRequisitoEmPeriodoPosterior() {
		Cadeira p1 = plano.getMapaCadeira().get("Programação I");
		Cadeira p2 = plano.getMapaCadeira().get("Programação II");
		
		try {
			plano.addCadeira(p1.getNome(), 10);
		} catch (Exception e) {
			Assert.fail("Nao devia ter falhado.");
		}
		//testa se eh pre-requisito de alguma cadeira alocadad.
		Assert.assertEquals(true, plano.isPreRequisito(p1.getNome()));
		//verifica se tem pre-requisito alocados erroneamente
		//metodo que fara a cadeira ficar vermelha
		Assert.assertEquals(true, plano.verificaPrerequisito(p2.getNome()));
		
		//TODO
		//testar cadeira que nao tem pre-requisitos
	}
}
