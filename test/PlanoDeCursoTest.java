import models.Cadeira;
import models.Periodo;
import models.PlanoDeCurso;

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
	public void testaListarPrimeiroPeriodo() {
		Periodo periodo = new Periodo(1);
		Assert.assertEquals(6, periodo.getCadeiras().size());
		// testar cadeiras
		// testar creditos
	}
	
	@Test
	public void testaAdicionarCadeira() {
		PlanoDeCurso plano = new PlanoDeCurso();
		plano.addPeriodo();

		try {
			plano.addCadeira("Programação II", 2);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		Assert.assertEquals(1, plano.getPeriodo(2).getCadeiras().size());
	}

	@Test
	public void testaCreditosDoPeriodo() throws Exception {
		PlanoDeCurso plano = new PlanoDeCurso();

		plano.addPeriodo(); // add periodo (2º periodo)
		plano.addCadeira("Programação II", 2);

		Assert.assertEquals(4, plano.getPeriodo(2).getCreditos());
	}

	@Test
	public void testaUltrapassarLimiteDeCreditos() throws Exception {
		PlanoDeCurso plano = new PlanoDeCurso();
		Cadeira cadeira = new Cadeira("Programação 2", 5);
		Cadeira cadeira2 = new Cadeira("Calculo 2", 5);
		Cadeira cadeira3 = new Cadeira("Linear", 5);
		Periodo periodo = new Periodo(2);

		plano.addPeriodo(); // add periodo (2º periodo)
		cadeira.setCreditos(10);
		cadeira2.setCreditos(10);

		periodo.addCadeira(cadeira);
		periodo.addCadeira(cadeira2);

		Assert.assertEquals(20, periodo.getCreditos());

		cadeira3.setCreditos(10);
		try {
			periodo.addCadeira(cadeira3);
		} catch (Exception e) {
			Assert.assertEquals("Limite de Créditos Ultrapassado",
					e.getMessage());
		}
	}

	@Test
	public void testaDificuldade() {
		PlanoDeCurso plano = new PlanoDeCurso();

		plano.addPeriodo();
		try {
			plano.addCadeira("Algebra Linear", 2); // dificuldade 9
			Assert.assertEquals(9, plano.getPeriodo(2).getDificuldadeTotal());

			plano.addCadeira("Cálculo II", 2); // dificuldade 7
			Assert.assertEquals(16, plano.getPeriodo(2).getDificuldadeTotal());
		} catch (Exception e) {

			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testaAddCadeiraComPreRequisitoEmPeriodoPosterior() {
		PlanoDeCurso plano = new PlanoDeCurso();
		plano.addPeriodo(); // periodo 2
		plano.addPeriodo(); // periodo 3

		try {
			plano.addCadeira("Cálculo II", 3);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		try {
			plano.addCadeira("Probabilidade e Est.", 2);
			Assert.fail("Devia ter falhado");
			// cálculo 2 é seu pre-requisito
		} catch (Exception e) {
			Assert.assertEquals("Pre Requisito: Cálculo II não concluido",
					e.getMessage());
		}
	}
}
