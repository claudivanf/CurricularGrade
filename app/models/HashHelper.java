package models;

import java.rmi.activation.ActivationException;

import org.mindrot.jbcrypt.BCrypt;

public class HashHelper {
	public static String criarSenha(String stringSenha)
			throws ActivationException {
		if (stringSenha == null) {
			throw new ActivationException("senha vazia");
		}
		return BCrypt.hashpw(stringSenha, BCrypt.gensalt());
	}

	public static boolean verificaSenha(String verificando,
			String encryptedSenha) {
		if (verificando == null) {
			return false;
		}
		if (encryptedSenha == null) {
			return false;
		}
		return BCrypt.checkpw(verificando, encryptedSenha);
	}
}
