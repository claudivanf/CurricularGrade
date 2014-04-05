//
//import models.Cadeira;
//import models.PlanoDeCurso;
//import models.Usuario;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import MOCK.GerenciadorDeCadeiras;
//
//public class SystemTest {
//	PlanoDeCurso plano;
//
//	@Test
//	public void remocaoDeDisciplinasTest() {
//
//		plano = new PlanoDeCurso();
//		plano.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
//		plano.removeCadeira("null");
//		plano.removeCadeira(null);
//
//		plano.removeCadeira("Programação I");
//
//		Assert.assertTrue(!plano.contains("Programação II"));
//		Assert.assertTrue(!plano.contains("Lab. de Programação II"));
//
//		Assert.assertTrue(plano.contains("Calc2"));
//		Assert.assertTrue(plano.contains("Calc1"));
//
//		plano.removeCadeira("Cálculo I");
//
//		Assert.assertTrue(!plano.contains("Cálculo II"));
//
//		plano = new PlanoDeCurso();
//		plano.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
//
//		plano.removeCadeira("Cálculo II");
//
//		Assert.assertTrue(!plano.contains("Cálculo II"));
//		Assert.assertTrue(plano.contains("Fund. de Física Clássica"));
//		Assert.assertTrue(plano.contains("Cálculo I"));
//
//		plano = new PlanoDeCurso();
//		try {
//			plano.addCadeira(null, 1);
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//			plano.addCadeira("Cálculo II", -1);
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//			plano.addCadeira("Cálculo I", 0);
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//			plano.addCadeira("Cálculo I", 123);
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//			plano.addCadeira("Cálculo 3000", 123);
//			Assert.fail();
//		} catch (Exception e) {
//		}
//
//	}
//
//	@Test
//	public void autenticacaoTest() {
//
//		Usuario usr = new Usuario("Carlos", "12345678");
//		PlanoDeCurso plano = new PlanoDeCurso();
//		plano.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
//		plano.setUsuario(usr);
//		try {
//			plano.setUsuario(null);
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//			plano.setUsuario(new Usuario(null, null));
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//			plano.setUsuario(new Usuario("", ""));
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//
//			usr.setPlano(null);
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//			usr.setNome(null);
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//			usr.setNome("");
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//			usr.setSenha(null);
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//			usr.setSenha("");
//			Assert.fail();
//		} catch (Exception e) {
//		}
//		try {
//			usr.setId(-12L);
//			Assert.fail();
//		} catch (Exception e) {
//		}
//
//		usr = new Usuario("Carlos 2", "12345678");
//		plano = new PlanoDeCurso();
//		plano.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
//
//		usr.setPlano(plano);
//		Assert.assertTrue(plano.getUsuario().equals(usr));
//
//		usr = new Usuario("Carlos 3", "12345678");
//		plano = new PlanoDeCurso();
//		plano.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
//
//		plano.setUsuario(usr);
//		Assert.assertTrue(usr.getPlano().equals(plano));
//
//	}
//
//	@Test
//	public void multiUsuarioTest() {
//
//		Usuario usr1 = new Usuario("Carlos 1", "12345678");
//		PlanoDeCurso plano1 = new PlanoDeCurso();
//		plano1.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
//		usr1.setPlano(plano1);
//
//		Usuario usr2 = new Usuario("Carlos 2", "12345678");
//		PlanoDeCurso plano2 = new PlanoDeCurso();
//		plano2.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
//		usr2.setPlano(plano2);
//
//		Usuario usr3 = new Usuario("Carlos 3", "12345678");
//		PlanoDeCurso plano3 = new PlanoDeCurso();
//		plano3.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
//		usr3.setPlano(plano3);
//
//		Usuario usr4 = new Usuario("Carlos 4", "12345678");
//		PlanoDeCurso plano4 = new PlanoDeCurso();
//		plano4.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
//		usr4.setPlano(plano4);
//		
//		//fiquei meio sem ideias aqui
//
//	}
//
//	@Test
//	public void passadoEFuturoTest() {
//
//		Usuario usr3 = new Usuario("Carlos 3", "12345678");
//		PlanoDeCurso plano3 = new PlanoDeCurso();
//		plano3.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
//		usr3.setPlano(plano3);
//
//		plano3.setPeriodoAtual(3);
//		Assert.assertTrue(plano3.getCadeirasConcluidas() == 0);
//		Assert.assertTrue(plano3.getCreditosConcluidos() == 0);
//		
//		plano3.getPeriodo(1).getCadeira("Programação I").setConcluida();
//		
//		Assert.assertTrue(plano3.getCadeirasConcluidas() == 1);
//		Assert.assertTrue(plano3.getCreditosConcluidos() == 4);
//		
//		
//		usr3 = new Usuario("Carlos 3", "12345678");
//		plano3 = new PlanoDeCurso();
//		plano3.distribuiCaderas(GerenciadorDeCadeiras.getListaDeCadeiras());
//		usr3.setPlano(plano3);
//		
//		plano3.setPeriodoAtual(2);
//		for (Cadeira c : plano3.getPeriodo(1).getListaCadeiras()) {
//			c.setConcluida();
//		}
//		
//		Assert.assertTrue(plano3.getPeriodoAtual().getCreditos() == 26); // 26 com base no xml
//
//	}
//
//}
