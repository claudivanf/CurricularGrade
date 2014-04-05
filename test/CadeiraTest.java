import models.Cadeira;

import org.junit.Assert;
import org.junit.Test;

public class CadeiraTest {

	@Test
	public void cadeiraTest() {

		// como eh uma classe com muitos gets e sets, os metodos funcionais de
		// cadeiras ficam restritos
		Cadeira c1 = new Cadeira("CadeiraTeste", 2);
		c1.setPeriodoOriginal(4);
		Assert.assertTrue(c1.getPeriodoOriginal() == 4);

		Cadeira c2 = new Cadeira("PreReq1", 3);
		Cadeira c3 = new Cadeira("PreReq2", 3);

		c2.addDependentes(c3);
		Assert.assertTrue(c2.getRequisitos().contains(c3) && c2.getRequisitos().size() == 1);

		c1.addDependentes(c2);
		Assert.assertTrue(c1.getRequisitos().size() == 1 && c1.getRequisitos().contains(c2)
				&& c1.getRequisitos().get(0).getRequisitos().contains(c3));
		Assert.assertTrue(c2.isPreRequisito(c3));
		Assert.assertTrue(c1.isPreRequisito(c2));

		Cadeira c4 = new Cadeira("CadeiraTeste", 2);
		Assert.assertTrue(c1.equals(c4));

	}

	// TODO   
	// TESTAR O EQUALS DE CADEIRA - FAZER PARA QUANDO É IGUAL E PARA QND NÃO É
	// em vez de fazer isso ->c2.getRequisitos().contains(c3) && c2.getRequisitos().size() == 1
	// faz 2 asserts
	// TESTA O COMPARE TO DE CADEIRA
	// TESTA CONSTRUTOR
}
