import models.Cadeira;
import models.Periodo;

import org.junit.Assert;
import org.junit.Test;

import exceptions.LimiteDeCreditosException;

public class PeriodoTest {

	@Test
	public void addAndRemoveChairsTest() {

		Periodo p1 = new Periodo();

		Assert.assertTrue(p1.isEmpty());

		Cadeira c1 = new Cadeira("teste1", 1);
		Cadeira c2 = new Cadeira("teste2", 1);
		Cadeira c3 = new Cadeira("teste3", 1);
		Cadeira c4 = new Cadeira("teste4", 1);
		Cadeira c5 = new Cadeira("teste5", 1);
		Cadeira c6 = new Cadeira("teste6", 1);
		Cadeira c7 = new Cadeira("teste7", 1);
		try {
			p1.addCadeira(c1);
			p1.addCadeira(c2);
			p1.addCadeira(c3);
			p1.addCadeira(c4);
			p1.addCadeira(c5);
			p1.addCadeira(c6);
			p1.addCadeira(c7);
		} catch (LimiteDeCreditosException e1) {
		}

		try {

			p1.addCadeira(new Cadeira("Teste8", 2));
			Assert.fail("Nao deveria ter permitido a adição de 8cadeiras de 4creditos");

		} catch (LimiteDeCreditosException e) {

		}

		try {
			p1.removerCadeira(c1);
			p1.removerCadeira(c2);
			p1.removerCadeira(c3);
			p1.removerCadeira(c4);
			p1.removerCadeira(c5);
			p1.removerCadeira(c6);
			p1.removerCadeira(c7);
		} catch (LimiteDeCreditosException e) {
			Assert.fail("Deveria ter permitido a remoção de 7cadeiras de 4creditos");
		}

		System.out.println(p1.getCreditos());
		Assert.assertTrue(p1.isEmpty());
		try {

			p1.removerCadeira(new Cadeira("Teste8", 2));

		} catch (LimiteDeCreditosException e) {

		}
		Assert.assertTrue(p1.isEmpty());

	}

	@Test
	public void getDificuldadeTest() {

		Cadeira c1 = new Cadeira("teste1", 1);
		Cadeira c2 = new Cadeira("teste2", 3);
		Cadeira c3 = new Cadeira("teste3", 4);
		Cadeira c4 = new Cadeira("teste4", 1);
		Cadeira c5 = new Cadeira("teste5", 2);
		Cadeira c6 = new Cadeira("teste6", 1);
		Cadeira c7 = new Cadeira("teste7", 1);

		Periodo p1 = new Periodo();
		try {
			p1.addCadeira(c1);
			p1.addCadeira(c2);
			p1.addCadeira(c3);
			p1.addCadeira(c4);
			p1.addCadeira(c5);
			p1.addCadeira(c6);
			p1.addCadeira(c7);
		} catch (LimiteDeCreditosException e1) {
			Assert.fail("Deveria ter permitido a adição de 7 cadeiras de 4creditos");
		}

		Assert.assertTrue(p1.getDificuldadeTotal() == 13);

	}

}
