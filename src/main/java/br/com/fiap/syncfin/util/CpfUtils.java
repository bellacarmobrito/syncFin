package br.com.fiap.syncfin.util;

public class CpfUtils {

    public static boolean isCpfValido(String cpf) {
        if (cpf == null) return false;

        String digitos = cpf.replaceAll("[^0-9]", "");
        if (digitos.length() != 11 || digitos.chars().distinct().count() == 1) {
            return false;
        }

        int[] n = digitos.chars().map(c -> c - '0').toArray();

        int soma = 0;
        for (int i = 0; i < 9; i++) soma += n[i] * (10 - i);
        int d1 = (soma * 10 % 11) % 10;

        soma = 0;
        for (int i = 0; i < 10; i++) soma += n[i] * (11 - i);
        int d2 = (soma * 10 % 11) % 10;

        return d1 == n[9] && d2 == n[10];
    }
}
