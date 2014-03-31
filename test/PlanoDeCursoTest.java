import models.Cadeira;
import models.PlanoDeCurso;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exceptions.LimiteDeCreditosException;
import exceptions.PeriodoCursandoException;

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
	public void setUp() throws LimiteDeCreditosException, PeriodoCursandoException {
		// cria e popula o plano a cada @Test
		// com os parâmetros originais da grade de CC.
		plano = new PlanoDeCurso();
		plano.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
		plano.setPeriodoCursando(2);
	}

	@Test
	public void testaDadosPlano() {
		Assert.assertEquals(10, plano.getPeriodos().size());
		Assert.assertEquals(55, plano.getCadeirasAlocadas().size());
		Assert.assertEquals(0, plano.getCadeirasDisponiveisOrdenadas().size());
	}

	@Test
	public void testaCadeirasESeusRequisitos() {
		Cadeira p1 = plano.getMapaDeCadeiras().get("Programação I");
		Cadeira p2 = plano.getMapaDeCadeiras().get("Programação II");
		Cadeira c1 = plano.getMapaDeCadeiras().get("Cálculo I");
		Cadeira c2 = plano.getMapaDeCadeiras().get("Cálculo II");

		Assert.assertEquals(true, c2.isPreRequisito(c1));
		Assert.assertEquals(true, p2.isPreRequisito(p1));
		Assert.assertEquals(false, p2.isPreRequisito(c1));
	}

	@Test
	public void testaListarPrimeiroPeriodo() {
		Cadeira p1 = plano.getMapaDeCadeiras().get("Programação I");
		Cadeira lp1 = plano.getMapaDeCadeiras().get("Lab. de Programação I");
		Cadeira ic = plano.getMapaDeCadeiras().get("Int. à Computacação");
		Cadeira lpt = plano.getMapaDeCadeiras().get("Leitura e Prod. de Textos");
		Cadeira c1 = plano.getMapaDeCadeiras().get("Cálculo I");
		Cadeira vet = plano.getMapaDeCadeiras().get("Algebra Vetorial");

		Assert.assertEquals(6, plano.getPeriodo(1).getCadeiras().size());
		Assert.assertEquals(p1, plano.getPeriodo(1).getCadeira(p1.getNome()));
		Assert.assertEquals(lp1, plano.getPeriodo(1).getCadeira(lp1.getNome()));
		Assert.assertEquals(ic, plano.getPeriodo(1).getCadeira(ic.getNome()));
		Assert.assertEquals(lpt, plano.getPeriodo(1).getCadeira(lpt.getNome()));
		Assert.assertEquals(c1, plano.getPeriodo(1).getCadeira(c1.getNome()));
		Assert.assertEquals(vet, plano.getPeriodo(1).getCadeira(vet.getNome()));

		Assert.assertEquals(24, plano.getPeriodo(1).getCreditos());
	}

	@Test
	public void testaAdicionarCadeira() {
		Cadeira p1 = plano.getMapaDeCadeiras().get("Programação I");
		Cadeira c1 = plano.getMapaDeCadeiras().get("Cálculo I");

		try {
			plano.addCadeira("Programação I", 2);
		} catch (LimiteDeCreditosException e) {
			Assert.assertEquals("Limite de Créditos Ultrapassado",
					e.getMessage());
		}
		try {
			plano.addCadeira("Cálculo I", 10);
			Assert.assertEquals(c1,
					plano.getPeriodo(10).getCadeira(c1.getNome()));
		} catch (LimiteDeCreditosException e) {
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

		Cadeira p1 = plano.getMapaDeCadeiras().get("Programação I");

		try {
			plano.addCadeira("Programação I", 10);
			Assert.assertEquals(p1,
					plano.getPeriodo(10).getCadeira(p1.getNome()));
		} catch (LimiteDeCreditosException e) {
			Assert.fail("nao devia ter lançado exceptio");
		}

		Assert.assertEquals(20, plano.getPeriodo(1).getCreditos());
		Assert.assertEquals(4, plano.getPeriodo(10).getCreditos());

	}

	@Test
	public void testaUltrapassarLimiteDeCreditos() throws Exception {
		// Cadeira p1 = plano.getMapaDeCadeiras().get("Programação I");

		try {
			plano.addCadeira("Programação I", 2);
			Assert.fail("nao devia ter passado");
		} catch (LimiteDeCreditosException e) {
			Assert.assertEquals("Limite de Créditos Ultrapassado",
					e.getMessage());
		}
	}

	@Test
	public void testaDificuldade() {
		Assert.assertEquals(25, plano.getPeriodo(1).getDificuldadeTotal());
		Assert.assertEquals(37, plano.getPeriodo(2).getDificuldadeTotal());
		Assert.assertEquals(50, plano.getPeriodo(3).getDificuldadeTotal());
		Assert.assertEquals(35, plano.getPeriodo(4).getDificuldadeTotal());
		Assert.assertEquals(43, plano.getPeriodo(5).getDificuldadeTotal());
		Assert.assertEquals(36, plano.getPeriodo(6).getDificuldadeTotal());
		Assert.assertEquals(27, plano.getPeriodo(7).getDificuldadeTotal());
		Assert.assertEquals(20, plano.getPeriodo(8).getDificuldadeTotal());
	}

	@Test
	public void testaAddCadeiraComPreRequisitoEmPeriodoPosterior() {
		Cadeira p1 = plano.getMapaDeCadeiras().get("Programação I");
		Cadeira p2 = plano.getMapaDeCadeiras().get("Programação II");

		Cadeira lpt = plano.getMapaDeCadeiras().get("Leitura e Prod. de Textos");
		try {
			plano.addCadeira(p1.getNome(), 10);
		} catch (Exception e) {
			Assert.fail("Nao devia ter falhado.");
		}
		// testa se eh pre-requisito de alguma cadeira alocadad.
		Assert.assertEquals(true, plano.isPreRequisito(p1.getNome()));
		// verifica se tem pre-requisito alocados erroneamente
		// metodo que fara a cadeira ficar vermelha
		Assert.assertEquals(true, plano.verificaPrerequisito(p2.getNome()));

		Assert.assertEquals(false, plano.verificaPrerequisito(p1.getNome()));
		Assert.assertEquals(false, plano.verificaPrerequisito(lpt.getNome()));
	}
}
