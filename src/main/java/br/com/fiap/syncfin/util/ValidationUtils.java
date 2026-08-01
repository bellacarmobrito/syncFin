package br.com.fiap.syncfin.util;

public class ValidationUtils {

    public static boolean algumEmBranco(String... valores) {
        for (String valor : valores) {
            if (valor == null || valor.isBlank()) return true;
        }
        return false;
    }
}
