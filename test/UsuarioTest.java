import models.Usuario;

import org.junit.Assert;
import org.junit.Test;

import exceptions.PeriodoCursandoException;

public class UsuarioTest {

	@Test
	public void userTest() {

		Usuario usr = new Usuario("teste@gmail.com", "teste", "123");

		// Assert.assertFalse(usr.autenticar("124"));
		// Assert.assertTrue(usr.autenticar("123"));

		try {
			usr.setPeriodoAtual(1);
			usr.setPeriodoAtual(2);
			usr.setPeriodoAtual(1);
			Assert.assertTrue(usr.getPeriodoAtual() == 1);
			usr.setPeriodoAtual(1);
			Assert.fail("Deveria ter causado exceção");
		} catch (PeriodoCursandoException e) {

		}

		Assert.assertTrue(usr.getSenha().equals("123"));
		usr.setSenha("12346");
		Assert.assertTrue(usr.getSenha().equals("12346"));
	}
}
