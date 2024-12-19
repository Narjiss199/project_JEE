package server.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthService {

    // Méthode pour hasher un mot de passe avec SHA-256
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString(); // Retourne le mot de passe haché en hexadécimal
    }

    // Vérifie si le mot de passe correspond au hash
    public boolean checkPassword(String inputPassword, String storedHash) throws NoSuchAlgorithmException {
        // String hashedInputPassword = hashPassword(inputPassword);
        return inputPassword.equals(storedHash);
    }

    public static void main(String[] args) {
        try {
            AuthService authService = new AuthService();
            String password = "monMotDePasseSecret";

            // Hacher le mot de passe
            String hashedPassword = authService.hashPassword(password);
            System.out.println("Mot de passe haché : " + hashedPassword);

            // Vérification du mot de passe
            boolean isPasswordCorrect = authService.checkPassword("monMotDePasseSecret", hashedPassword);
            if (isPasswordCorrect) {
                System.out.println("Le mot de passe est correct.");
            } else {
                System.out.println("Le mot de passe est incorrect.");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
