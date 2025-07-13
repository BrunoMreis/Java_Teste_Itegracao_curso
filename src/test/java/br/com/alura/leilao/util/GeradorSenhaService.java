package br.com.alura.leilao.util;

import java.security.SecureRandom;

public class GeradorSenhaService {


    public static String geraStringSegura(int tamanho) {
        String CARACTERES = "ABCÇDEFGHIJKLMNOPQRSTUVWXYZabcçdefghijklmnopqrstuvwxyz0123456789!@#$%&*";
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++) {
            int index = random.nextInt(CARACTERES.length());
            senha.append(CARACTERES.charAt(index));
        }
        return senha.toString();
    }

}
