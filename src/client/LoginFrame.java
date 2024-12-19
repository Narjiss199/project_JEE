package client;

import server.auth.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private AuthService authService;

    public LoginFrame() {
        authService = new AuthService();  // Initialisation du service d'authentification

        // Configuration de la fenêtre
        setTitle("Login");
        setSize(300, 200);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création des composants de l'interface
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Login");

        // Création d'un panneau pour organiser les composants
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel(""));  // Cellule vide pour l'alignement
        panel.add(loginButton);

        // Ajout du panneau à la fenêtre
        add(panel);

        // Ajouter un gestionnaire d'événements pour le bouton de connexion
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Vous devez ici vérifier le mot de passe haché de la base de données ou fichier
        // Exemple : récupérer le mot de passe haché pour cet utilisateur depuis la base de données
        String storedHashedPassword = getStoredHashedPassword(username);  // Méthode fictive, à remplacer par l'accès réel à la base de données

        try {
            if (authService.checkPassword(password, storedHashedPassword)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                // Passer à l'écran suivant (MainFrame)
                openMainFrame();
                dispose(); // Fermer la fenêtre de login
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(this, "Error during password verification.");
            ex.printStackTrace();
        }
    }

    // Méthode fictive pour obtenir le mot de passe haché de l'utilisateur
    private String getStoredHashedPassword(String username) {
        // Ici, vous devez accéder à votre base de données et récupérer le mot de passe haché de cet utilisateur
        // Par exemple, si l'utilisateur s'appelle "admin", le mot de passe haché serait celui que vous avez précédemment stocké
        if (username.equals("admin")) {
            return "admin";  // Remplacez par le mot de passe réel stocké
        }
        return null;  // Si l'utilisateur n'existe pas
    }
    // Ouvrir le MainFrame après un login réussi
    private void openMainFrame() {
        MainFrame mainFrame = new MainFrame(); // Créer le MainFrame
        mainFrame.setVisible(true); // Afficher le MainFrame
    }

    public static void main(String[] args) {
        // Lancer l'application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
