package com.example.SmartBuy.security;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "secreto";

    // Cria JWT
    public static String gerarToken(String subject, long expirationMillis) throws Exception {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + expirationMillis;

        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = String.format("{\"sub\":\"%s\",\"iat\":%d,\"exp\":%d}", subject, nowMillis / 1000, expMillis / 1000);

        String base64Header = Base64.getUrlEncoder().withoutPadding().encodeToString(header.getBytes());
        String base64Payload = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes());

        String unsignedToken = base64Header + "." + base64Payload;
        String signature = sign(unsignedToken, SECRET_KEY);

        return unsignedToken + "." + signature;
    }

    // Verifica JWT
    public static boolean verificaToken(String token) throws Exception {
        String[] parts = token.split("\\.");
        if (parts.length != 3) return false;

        String unsignedToken = parts[0] + "." + parts[1];
        String signature = sign(unsignedToken, SECRET_KEY);

        if (!signature.equals(parts[2])) return false;

        // Verifica expiração
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        long exp = Long.parseLong(payloadJson.replaceAll(".*\"exp\":(\\d+).*", "$1"));
        long now = System.currentTimeMillis() / 1000;
        return now < exp;
    }

    // Assinatura com HMAC SHA-256
    private static String sign(String data, String secret) throws Exception {
        Mac hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        hmac.init(keySpec);
        byte[] signatureBytes = hmac.doFinal(data.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
    }
}