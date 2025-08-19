package com.example.SmartBuy.security;

import java.security.MessageDigest;

public class CriptografiaUtil {
    public static String gerarHash(String senha){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b: hash){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static boolean verificarSenha(String senhaDigitada, String senhaHashArmazenada) {
        String hashDigitado = gerarHash(senhaDigitada);
        return hashDigitado.equals(senhaHashArmazenada);
    }
}
