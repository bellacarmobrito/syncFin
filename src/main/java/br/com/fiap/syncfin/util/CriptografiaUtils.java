package br.com.fiap.syncfin.util;

import org.mindrot.jbcrypt.BCrypt;

public class CriptografiaUtils {

    public static String criptografar(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public static boolean verificar(String senhaDigitada, String hashArmazenado) {
        return BCrypt.checkpw(senhaDigitada, hashArmazenado);
    }
}
