package br.com.fiap.syncfin.teste;

import br.com.fiap.syncfin.util.CriptografiaUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class CriptografiaTeste {

    public static void main(String[] args) {

        try {
            System.out.println(CriptografiaUtils.criptografar("Tabela01"));
            System.out.println(CriptografiaUtils.criptografar("Tabela01"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }
}
