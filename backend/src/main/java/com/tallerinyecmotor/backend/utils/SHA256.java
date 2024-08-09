package com.tallerinyecmotor.backend.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class SHA256 {

 // Método para cifrar la contraseña
    public static String hashPassword(String password) {
        try {

            EnvConfig envConfig = new EnvConfig();
            // Crear un objeto Mac para HMAC-SHA256
            Mac mac = Mac.getInstance("HmacSHA256");

            String keyHard = envConfig.getKey();

            // Crear una clave secreta a partir de la cadena de la clave
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyHard.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

            // Inicializar el objeto Mac con la clave secreta
            mac.init(secretKeySpec);

            // Generar el HMAC
            byte[] hmacBytes = mac.doFinal(password.getBytes(StandardCharsets.UTF_8));

            // Convertir el HMAC a una cadena Base64 para facilitar el almacenamiento y visualización
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar HMAC-SHA256", e);
        }
    }
    // Método para comparar la contraseña ingresada con la almacenada
    public static boolean verifyPassword(String password) {
        EnvConfig envConfig = new EnvConfig();

        String passOriginal = envConfig.getPass();
        // Cifrar la contraseña ingresada
        String hashedPass = hashPassword(passOriginal);

        // Comparar los hashes
        return password.equals(hashedPass);
    }
}
